package dev.ector.features.users

import dev.ector.database.postgres.PostgresDb
import dev.ector.features.users.data.UsersRepo
import dev.ector.features.users.domain.controller.UsersController
import dev.ector.features.users.domain.interfaces.IUsersController
import dev.ector.features.users.domain.interfaces.IUsersRepo
import org.koin.core.module.Module
import org.koin.dsl.module

val usersModule: Module = module {
    single<IUsersRepo> { UsersRepo() }
    single<IUsersController> {
        UsersController(
            get<PostgresDb>(),
            get<IUsersRepo>(),
        )
    }
}