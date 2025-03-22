package dev.ector.features._shared

import io.ktor.server.application.*

class AppConfig(environment: ApplicationEnvironment) {
    val address = "${environment.config.host}:${environment.config.port}"
    val jwtSecret = environment.config.property("jwt.secret").getString()
    val jwtIssuer = environment.config.property("jwt.issuer").getString()
    val jwtAudience = environment.config.property("jwt.audience").getString()
    val jwtRealm = environment.config.property("jwt.realm").getString()
    val mysqlHost = environment.config.property("mysql.host").getString()
    val mysqlUser = environment.config.property("mysql.user").getString()
    val mysqlPassword = environment.config.property("mysql.password").getString()
    val postgresHost = environment.config.property("postgres.host").getString()
    val postgresUser = environment.config.property("postgres.user").getString()
    val postgresPassword = environment.config.property("postgres.password").getString()
    val uploadsZapsSparesImages = environment.config.property("uploads.zaps.spares.images").getString()
}