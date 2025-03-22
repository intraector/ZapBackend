package dev.ector.features.roles

import dev.ector.database.postgres.PostgresDb
import dev.ector.features.roles.data.RolesInMemoryRepo
import dev.ector.features.roles.data.RolesPersistentRepo
import dev.ector.features.roles.domain.controller.RolesController
import dev.ector.features.roles.domain.interfaces.IRolesController
import org.koin.core.module.Module
import org.koin.dsl.module

val rolesModule: Module = module {
    single<IRolesController> {
        RolesController(
            get<PostgresDb>(),
            RolesInMemoryRepo(),
            RolesPersistentRepo(),
        )
    }


}