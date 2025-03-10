package dev.ector.features.auth

import dev.ector.database.postgres.PostgresDb
import dev.ector.features.auth.data.AuthRepo
import dev.ector.features.auth.domain.controller.AuthController
import dev.ector.features.auth.domain.interfaces.IAuthController
import dev.ector.features.users.domain.interfaces.IUsersRepo
import org.koin.core.module.Module
import org.koin.dsl.module

val authModule: Module = module {
    single<IAuthController> {
        AuthController(
            get<PostgresDb>(),
            AuthRepo(),
            get<IUsersRepo>(),
        )
    }
}