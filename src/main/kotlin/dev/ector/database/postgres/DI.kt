package dev.ector.database.postgres

import org.jetbrains.exposed.sql.Database
import org.koin.core.module.Module
import org.koin.dsl.module


fun postgresModule(): Module {
    val db = Database.connect(
        "jdbc:postgresql://localhost:5432/training",
        driver = "org.postgresql.Driver",
        user = "postgres",
        password = "159263"
    )
    return module {
        single { PostgresDb(db) }
    }
}