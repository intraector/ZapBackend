package dev.ector.features._shared

import io.ktor.server.application.*

class AppConfig(environment: ApplicationEnvironment) {
    val address = "${environment.config.host}:${environment.config.port}"
    val jwtSecret = environment.config.property("jwt.secret").getString()
    val jwtIssuer = environment.config.property("jwt.issuer").getString()
    val jwtAudience = environment.config.property("jwt.audience").getString()
    val jwtRealm = environment.config.property("jwt.realm").getString()
}