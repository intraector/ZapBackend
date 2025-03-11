package dev.ector.features.auth


import dev.ector.features.auth.data.JwtService
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import org.koin.ktor.ext.inject

fun Application.configureSecurity() {
    val jwtService : JwtService by inject()
    authentication {
        jwt {
            realm = jwtService.realm
            verifier(jwtService.jwtVerifier)

            validate {
                jwtService.customValidator(it)
            }
        }

        jwt("another-auth") {
            realm = jwtService.realm
            verifier(jwtService.jwtVerifier)

            validate {
                jwtService.customValidator(it)
            }
        }
    }
}