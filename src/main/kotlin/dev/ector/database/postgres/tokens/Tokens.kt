package dev.ector.database.postgres.tokens

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction

object Tokens : Table() {

    val id = integer("id").autoIncrement()
    private val account = varchar("account", 25)
    private val token = Tokens.varchar("token", 50)

    override val primaryKey = PrimaryKey(id)

    fun insert(userDto: TokenDto) {
        transaction {
            Tokens.insert {
                it[account] = userDto.account
                it[token] = userDto.token
            }
        }
    }

}