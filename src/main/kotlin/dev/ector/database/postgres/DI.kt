package dev.ector.database.postgres

import org.jetbrains.exposed.sql.Database
import org.koin.dsl.module


val postgresModule = {
    val db = Database.connect(
        "jdbc:postgresql://localhost:5432/training",
        driver = "org.postgresql.Driver",
        user = "postgres",
        password = "159263"
    )
    module {
        single { PostgresDb(db) }
    }
}