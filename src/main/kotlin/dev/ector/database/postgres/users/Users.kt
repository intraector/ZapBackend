package dev.ector.database.postgres.users

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

object Users : Table() {

    private val account = Users.varchar("account", 25)
    private val password = Users.varchar("password", 25)
    private val name = Users.varchar("name", 30)
    private val email = Users.varchar("email", 25).nullable()

    fun insert(userDto: UserDto) {
        transaction {
            Users.insert {
                it[account] = userDto.account
                it[password] = userDto.password
                it[name] = userDto.name
                it[email] = userDto.email
            }
        }
    }

    fun fetch(login: String): UserDto? {
        try {
        val result = transaction {
            Users.selectAll().where { Users.account.eq(login) }.single()
        }
            return UserDto(
                account = result[account],
                password = result[password],
                name = result[name],
                email = result[email]
            )

        } catch (e: NoSuchElementException) {
            return null;
        }
    }
}