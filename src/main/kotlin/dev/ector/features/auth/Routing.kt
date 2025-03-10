package dev.ector.features.auth

import dev.ector.database.postgres.PostgresDb
import dev.ector.features._shared.exceptions.RequiredParameterException
import dev.ector.features._shared.exceptions.WrongCodeException
import dev.ector.features._shared.extensions.FieldName
import dev.ector.features._shared.extensions.requireNonNull
import dev.ector.features._shared.validators.isValidPhone
import dev.ector.features.auth.data.AuthRepo
import dev.ector.features.auth.domain.controller.AuthController
import dev.ector.features.auth.domain.models.PhoneCodeReq
import dev.ector.features.users.domain.interfaces.IUsersRepo
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.configureRoutingAuth() {
    val postgres: PostgresDb by inject()
    val usersRepo: IUsersRepo by inject()
    val controller = AuthController(postgres, AuthRepo(), usersRepo)
    routing {
        route("/api/v1/auth") {

            get("/get_phone_code") {
                call.queryParameters
                    .requireNonNull(FieldName.PHONE)
                val phone = call.queryParameters[FieldName.PHONE]!!
                controller.createPhoneCode(phone)
                call.respond(HttpStatusCode.OK)
            }

            post("/sign_in_with_phone") {
                val req = call.receive<PhoneCodeReq>()
                if (req.code.length != 6) {
                    throw WrongCodeException()
                }
                if (!req.phone.isValidPhone) {
                    throw RequiredParameterException("phone")
                }

                controller.signInWithPhone(req)
                call.respond(HttpStatusCode.OK)
            }

        }
    }
}