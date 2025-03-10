package dev.ector.features.users

import dev.ector.features._shared.extensions.pageNumber
import dev.ector.features._shared.extensions.pageSize
import dev.ector.features.users.domain.interfaces.IUsersController
import dev.ector.features.users.domain.models.UsersReq
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.configureRoutingUsers() {
    val controller by inject<IUsersController>()
    routing {
        get("/api/v1/users") {
            val req = UsersReq(
                pageNumber = call.parameters.pageNumber(),
                pageSize = call.parameters.pageSize(),
            )
            val output = controller.fetch(req)
            call.respond(HttpStatusCode.OK, output)


        }
    }
}