package dev.ector.database.postgres.tokens

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentDateTime
import org.jetbrains.exposed.sql.kotlin.datetime.datetime
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.update
import java.util.*

object RefreshTokensTable : IntIdTable() {

    private val userId = integer("user_id")
    private val token = varchar("token", 50)
    private val createdAt = datetime("created_at").defaultExpression(CurrentDateTime)


    fun create(userId: Int): RefreshTokenDto {
        val row = insert {
            it[this.userId] = userId
            it[token] = UUID.randomUUID().toString()
        }.resultedValues!!.first()

        return RefreshTokenDto(
            id = row[id].value,
            token = row[token],
            userId = row[this.userId],
            createdAt = row[createdAt]
        )
    }

    fun fetchByToken(token: String): RefreshTokenDto? {
        return selectAll()
            .where {
                RefreshTokensTable.token eq token
            }.singleOrNull()
            ?.let {
                RefreshTokenDto(
                    id = it[id].value,
                    userId = it[userId],
                    token = it[this.token],
                    createdAt = it[createdAt]
                )
            }
    }

    fun renewToken(token: String): RefreshTokenDto? {
        val newToken = UUID.randomUUID().toString()

        val rowsUpdated = update({ RefreshTokensTable.token eq token }) {
            it[this.token] = newToken
        }

        if (rowsUpdated == 0) return null

        return fetchByToken(newToken)
    }

    fun deleteByUserId(userId: Int) {
        deleteWhere {
            RefreshTokensTable.userId eq userId
        }
    }

}