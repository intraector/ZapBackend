package dev.ector.features.zaps

import dev.ector.features._shared.extensions.*
import dev.ector.features.zaps.domain.interfaces.IZapController
import dev.ector.features.zaps.domain.models.ZapsReq
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import java.io.File

fun Application.configureRoutingZaps() {
    val controller by inject<IZapController>()
    routing {
        route("/api/v1/zaps") {
            staticFiles("/images", File("/Users/intraector/dev/apps/backend/db/files/spares"))
            get("") {
                val req = ZapsReq(
                    pageNumber = call.parameters.pageNumber(),
                    pageSize = call.parameters.pageSize(),
                )
                val output = controller.fetch(req)
                call.respond(HttpStatusCode.OK, output)
            }

            post("") {
                val multipartData = call.receiveMultipart(formFieldLimit = 1024 * 1024)
                val output = controller.create(multipartData)
                call.respond(HttpStatusCode.OK, output)
            }

            delete("/{${F.ID}}") {
                call.pathParameters
                    .requireNonNull(F.ID)
                    .andAssert { it.toIntOrNull() != null }
                controller.delete(call.pathParameters[F.ID]!!.toInt())
                call.respond(HttpStatusCode.OK)
            }


        }
    }
}