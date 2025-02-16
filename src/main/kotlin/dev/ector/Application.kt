package dev.ector

import address
import dev.ector.database.mysql.mysqlModule
import dev.ector.database.postgres.dict.region.RegionTable
import dev.ector.database.postgres.postgresModule
import dev.ector.database.postgres.spares.SparesTable
import dev.ector.database.postgres.zaps.ZapsTable
import dev.ector.features._shared.AppConfig
import dev.ector.features._shared.exceptions.AuthenticationException
import dev.ector.features._shared.exceptions.AuthorizationException
import dev.ector.features._shared.exceptions.RequiredParameterException
import dev.ector.features.auth.configureRoutingAuth
import dev.ector.features.dict.configureRoutingDict
import dev.ector.features.dict.dictModule
import dev.ector.features.zaps.configureRoutingZaps
import dev.ector.features.zaps.zapsModule
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    install(Koin) {
        slf4jLogger()

        modules(
            module {
                single {
                    AppConfig(
                        address = environment.config.address,
                    )
                }
            },
            mysqlModule(),
            postgresModule(),
            dictModule,
            zapsModule,
        )
    }
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            when (cause) {
                is IllegalArgumentException -> call.respondText(
                    text = "400: ${cause.message}", status = HttpStatusCode.BadRequest
                )

                is RequiredParameterException -> call.respondText(
                    text = "Required: ${cause.message}", status = HttpStatusCode.BadRequest
                )

                is AuthenticationException -> call.respondText(
                    text = "401: $cause", status = HttpStatusCode.Unauthorized
                )

                is AuthorizationException -> call.respondText(
                    text = "403: $cause", status = HttpStatusCode.Forbidden
                )


                else -> call.respondText(text = "500: $cause", status = HttpStatusCode.InternalServerError)
            }
        }
    }

    configureSerialization()
    configureRouting()
    configureRoutingAuth()
    configureRoutingDict()
    configureRoutingZaps()

    transaction {
        SchemaUtils.create(RegionTable)
        SchemaUtils.create(ZapsTable)
        SchemaUtils.create(SparesTable)
    }
}