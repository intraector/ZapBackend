package dev.ector.features.zaps.domain.controller

import dev.ector.database.postgres.PostgresDb
import dev.ector.features._shared.exceptions.RequiredParameterException
import dev.ector.features._shared.extensions.FieldName
import dev.ector.features.zaps.domain.interfaces.IZapController
import dev.ector.features.zaps.domain.interfaces.IZapsRepo
import dev.ector.features.zaps.domain.models.Zap
import dev.ector.features.zaps.domain.models.ZapsReq
import dev.ector.features.zaps.domain.models.ZapsResp
import io.ktor.http.content.*
import io.ktor.util.cio.*
import io.ktor.utils.io.*
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.sql.transactions.transaction
import java.io.File
import java.util.*

class ZapController(
    val repo: IZapsRepo,
    val address: String,
    val postgres: PostgresDb,
) : IZapController {
    override suspend fun create(data: MultiPartData): Zap {
        val savedFiles = mutableListOf<File>()
        val format = Json { prettyPrint = true }
        val zapSparesPhotos = mutableMapOf<Int, MutableList<String>>()
        var zap: Zap? = null


        try {
            data.forEachPart { part ->
                when (part) {
                    is PartData.FormItem -> {
                        if (part.name == FieldName.ZAP) {
                            zap = format.decodeFromString(Zap.serializer(), part.value)
                            println(zap)
                        }
                    }

                    is PartData.FileItem -> {
                        // We need spareId to determine which spare the photo belongs to
                        val spareId = part.name?.toIntOrNull()
                        if (spareId == null) {
                            throw RequiredParameterException("photo ${FieldName.SPARE_ID}")
                        }

                        // Get the file name
                        val name = mutableListOf<String>()
                        name.add(UUID.randomUUID().toString())
                        val fileType = part.contentType?.toString()?.split("/")?.getOrNull(1)
                        fileType?.let { name.add(it) }

                        // Get the photo URL
                        val photoUrl = "http://$address/api/v1/zaps/images/${name.joinToString(".")}"
                        val sparesPhotos = zapSparesPhotos[spareId] ?: mutableListOf<String>()
                        sparesPhotos.add(photoUrl)
                        zapSparesPhotos[spareId] = sparesPhotos

                        // Save the file
                        val file = File("/Users/intraector/dev/apps/backend/db/files/spares/${name.joinToString(".")}")
                        savedFiles.add(file)
                        val parentDir = file.parentFile
                        if (parentDir != null && !parentDir.exists()) {
                            parentDir.mkdirs()
                        }
                        try {

                            part.provider().copyAndClose(file.writeChannel())
                        } catch (e: Exception) {
                            println(e)
                        }
                    }

                    else -> Unit
                }
            }

            zap ?: throw RequiredParameterException(FieldName.ZAP)


            // Add the photos to the spares
            for ((spareId, urls) in zapSparesPhotos) {
                zap.spares[spareId].photos.run {
                    clear()
                    addAll(urls)
                }
            }

            return repo.create(zap)

        } catch (e: Exception) {
            println(e)
            savedFiles.forEach { it.delete() }
            throw e
        } finally {
            data.forEachPart { part ->
                part.dispose()
            }
        }

    }

    override suspend fun fetch(req: ZapsReq): ZapsResp {
        return transaction(postgres.db) {
            repo.fetch(req)
        }
    }

    override suspend fun delete(id: Int) {
        transaction(postgres.db) {
            repo.delete(id)
        }
    }
}