package dev.ector.features.auth


import dev.ector.features.auth.data.JwtService
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import org.koin.ktor.ext.inject

fun Application.configureSecurity() {
    val jwtService: JwtService by inject()
    authentication {
        jwt {
            verifier(jwtService.jwtVerifier)

            validate {
                jwtService.customValidator(it)
            }

        }


    }

}