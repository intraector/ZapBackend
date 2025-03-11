package dev.ector.features.auth

import dev.ector.database.postgres.PostgresDb
import dev.ector.features._shared.AppConfig
import dev.ector.features.auth.data.AuthRepo
import dev.ector.features.auth.data.JwtService
import dev.ector.features.auth.domain.controller.AuthController
import dev.ector.features.auth.domain.interfaces.IAuthController
import dev.ector.features.users.domain.interfaces.IUsersRepo
import io.ktor.server.application.*
import org.koin.core.module.Module
import org.koin.dsl.module

val Application.authModule: Module
    get() = module {
        single<IAuthController> {
            AuthController(
                get<PostgresDb>(),
                AuthRepo(),
                get<IUsersRepo>(),
                get<JwtService>(),
            )
        }
        single {
            JwtService(
                get<IUsersRepo>(),
                get<PostgresDb>(),
                get<AppConfig>()
            )
        }
    }