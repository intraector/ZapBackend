package dev.ector.features.roles

import org.koin.core.module.Module
import org.koin.dsl.module

val rolesModule: Module = module {
    single {
        RoutesRoles()
    }


}