package dev.ector.database.mysql

import org.jetbrains.exposed.sql.Database
import org.koin.core.module.Module
import org.koin.dsl.module


fun mysqlModule(): Module {
    val db = Database.connect(
        "jdbc:mysql://localhost:3306/dictdb",
        driver = "com.mysql.cj.jdbc.Driver",
        user = "root",
        password = ""
    )
    return module {
        single { MysqlDb(db) }
    }


}