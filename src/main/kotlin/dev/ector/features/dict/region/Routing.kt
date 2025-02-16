package dev.ector.features.dict.region

import dev.ector.features._shared.exceptions.AlreadyExistsException
import dev.ector.features._shared.extensions.FieldName
import dev.ector.features._shared.extensions.andAssert
import dev.ector.features._shared.extensions.requireNonNull
import dev.ector.features.dict.region.domain.inteface.IRegionDictRepo
import dev.ector.features.dict.region.domain.models.Region
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.configureRoutingDictRegion() {
    val repo: IRegionDictRepo by inject()
    routing {
        route("/api/v1/dict/region") {
            get {
                val output = repo.fetch()
                call.respond(HttpStatusCode.OK, output)
            }
        }
        route("/api/v1/admin/dict/region") {
            get {
                val output = repo.fetchFull()
                println(output)
                call.respond(HttpStatusCode.OK, output)
            }

            post {
                val req = call.receive<List<Region.Full>>()
                try {
                    repo.insert(req)
                } catch (e: AlreadyExistsException) {
                    call.respond(HttpStatusCode.Conflict, "Id already exists: ${e.message}")
                }
                call.respond(HttpStatusCode.OK)
            }

            delete("/{${FieldName.ID}}") {
                call.pathParameters
                    .requireNonNull(FieldName.ID)
                    .andAssert { it.toIntOrNull() != null }
                repo.delete(call.pathParameters[FieldName.ID]!!.toInt())
                call.respond(HttpStatusCode.OK)
            }

            put {
                val req = call.receive<List<Region.Full>>()
                repo.update(req)
                call.respond(HttpStatusCode.OK)
            }

        }
    }
}