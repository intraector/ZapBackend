package dev.ector.database.mysql

import org.jetbrains.exposed.sql.Database
import org.koin.dsl.module


val mysqlModule = {
    val db = Database.connect(
        "jdbc:mysql://localhost:3306/dictdb",
        driver = "com.mysql.cj.jdbc.Driver",
        user = "root",
        password = ""
    )
    module {
        single { MysqlDb(db) }
    }


}