package dev.ector.features.zaps.data

import dev.ector.database.postgres.PostgresDb
import dev.ector.database.postgres.spares.SparesTable
import dev.ector.database.postgres.zaps.ZapsTable
import dev.ector.features.zaps.data.convert.toSpare
import dev.ector.features.zaps.data.convert.toSpareDto
import dev.ector.features.zaps.data.convert.toZap
import dev.ector.features.zaps.data.convert.toZapDto
import dev.ector.features.zaps.domain.interfaces.IZapsRepo
import dev.ector.features.zaps.domain.models.Zap
import dev.ector.features.zaps.domain.models.ZapsReq
import dev.ector.features.zaps.domain.models.ZapsResp
import org.jetbrains.exposed.sql.transactions.transaction

class ZapsRepo(val postgres: PostgresDb) : IZapsRepo {

    override suspend fun fetch(req: ZapsReq): ZapsResp {
        val zaps = transaction(postgres.db) {
            // Get all zaps
            val zaps = ZapsTable.fetch(req).map { it.toZap() }
            // Get all spares
            val ids = zaps.mapNotNull { it.id }.toList()
            val spares = SparesTable.fetch(ids).map { it.toSpare() }
            // Assign spares to zaps
            zaps.forEach { zap ->
                zap.spares = spares.filter { it.zapId == zap.id }
            }

            return@transaction zaps
        }

        return ZapsResp(
            pageNumber = req.pageNumber,
            pageSize = req.pageSize,
            noMorePages = zaps.size < req.pageSize,
            data = zaps,
        )
    }

    override suspend fun create(req: Zap): Zap {
        return transaction(postgres.db) {
            // Insert zap
            val insertedId = ZapsTable.insert(req.toZapDto())

            // Update zap with inserted id
            val req = req.copy(id = insertedId)
            req.spares.forEach { it.zapId = insertedId }

            // Insert spares
            val sparesIds = SparesTable.insert(
                req.spares.map { it.toSpareDto() }
            )
            // Update spares with inserted ids
            req.spares.forEachIndexed { index, spare ->
                spare.id = sparesIds[index]
            }
            return@transaction req
        }
    }

    override suspend fun delete(id: Int) {
        transaction(postgres.db) {
            SparesTable.delete(id)
            ZapsTable.delete(id)
        }
    }


}