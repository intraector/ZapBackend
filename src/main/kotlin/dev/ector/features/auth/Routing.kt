package dev.ector.features.auth

import dev.ector.features._shared.S
import dev.ector.features._shared.div
import dev.ector.features._shared.exceptions.RequiredParameterException
import dev.ector.features._shared.exceptions.WrongCodeException
import dev.ector.features._shared.extensions.F
import dev.ector.features._shared.extensions.requireNonNull
import dev.ector.features._shared.validators.isValidPhone
import dev.ector.features.auth.domain.interfaces.IAuthController
import dev.ector.features.auth.domain.models.PhoneCodeReq
import dev.ector.features.auth.domain.models.RefreshToken
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.configureRoutingAuth() {
    val controller: IAuthController by inject()
    routing {
        route(S.apiV1 / S.auth) {

            post("" / S.refresh_token) {
                val req = call.receive<RefreshToken>()
                val newToken = controller.renewTokens(req.token)
                if (newToken == null) {
                    call.respond(HttpStatusCode.Unauthorized)
                } else {
                    call.respond(HttpStatusCode.OK, newToken)
                }
            }

            get("" / S.phone_code) {
                call.queryParameters
                    .requireNonNull(F.PHONE)
                val phone = call.queryParameters[F.PHONE]!!
                controller.createPhoneCode(phone)
                call.respond(HttpStatusCode.OK)
            }

            post("" / S.sign_in_with_phone) {
                val req = call.receive<PhoneCodeReq>()
                if (req.code.length != 6) {
                    throw WrongCodeException()
                }
                if (!req.phone.isValidPhone) {
                    throw RequiredParameterException("phone")
                }

                val tokens = controller.signInWithPhone(req)
                call.respond(HttpStatusCode.OK, tokens)
            }

        }
    }
}