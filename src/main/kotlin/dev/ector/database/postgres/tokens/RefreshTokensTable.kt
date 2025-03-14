package dev.ector.database.postgres.tokens

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentDateTime
import org.jetbrains.exposed.sql.kotlin.datetime.datetime
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.update

object RefreshTokensTable : IntIdTable(name = "refresh_tokens") {

    private val userId = integer("user_id")
    private val token = varchar("token", 1000)
    private val createdAt = datetime("created_at").defaultExpression(CurrentDateTime)


    fun insert(dto: RefreshTokenDto): RefreshTokenDto {
        val row = insert {
            it[userId] = dto.userId
            it[token] = dto.token
        }.resultedValues!!.first()

        return RefreshTokenDto(
            id = row[id].value,
            token = row[token],
            userId = row[userId],
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

    fun update(token: RefreshTokenDto): RefreshTokenDto? {

        val rowsUpdated = update({ userId eq token.userId }) {
            it[this.token] = token.token
        }

        if (rowsUpdated == 0) return null

        return fetchByToken(token.token)
    }

    fun deleteByUserId(userId: Int) {
        deleteWhere {
            RefreshTokensTable.userId eq userId
        }
    }

}