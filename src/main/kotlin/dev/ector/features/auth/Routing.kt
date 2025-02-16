package dev.ector.features.auth

import dev.ector.features.auth.reg.models.RegReq
import dev.ector.features.auth.reg.validate.isValidAccount
import dev.ector.features.auth.reg.validate.isValidName
import dev.ector.features.auth.sign_in.models.SigninReq
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRoutingAuth() {
    routing {
        route("/auth") {
            post("/register") {
                val req = call.receive<RegReq>()
                if (!req.account.isValidAccount()) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid account")
                    return@post
                }
                if (req.name.isValidName() != true) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid name")
                    return@post
                }

                AuthController().register(call, req)
            }

            post("/sign_in") {
                val req = call.receive<SigninReq>()
                AuthController().signIn(call, req)
            }
        }

    }
}