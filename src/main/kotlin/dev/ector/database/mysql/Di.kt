package dev.ector.database.mysql

import dev.ector.features._shared.AppConfig
import org.jetbrains.exposed.sql.Database
import org.koin.core.module.Module
import org.koin.dsl.module


fun mysqlModule(config: AppConfig): Module {
    val db = Database.connect(
        "jdbc:mysql://${config.mysqlHost}",
        driver = "com.mysql.cj.jdbc.Driver",
        user = config.mysqlUser,
        password = config.mysqlPassword
    )
    return module {
        single { MysqlDb(db) }
    }


}