package dev.ector.database.postgres

import dev.ector.features._shared.AppConfig
import org.jetbrains.exposed.sql.Database
import org.koin.core.module.Module
import org.koin.dsl.module


fun postgresModule(config: AppConfig): Module {
    val db = Database.connect(
        "jdbc:postgresql://${config.postgresHost}",
        driver = "org.postgresql.Driver",
        user = config.postgresUser,
        password = config.postgresPassword
    )
    return module {
        single { PostgresDb(db) }
    }
}