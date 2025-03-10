package dev.ector.database.postgres.users

import dev.ector.features.users.domain.models.UsersReq
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentDateTime
import org.jetbrains.exposed.sql.kotlin.datetime.datetime
import org.jetbrains.exposed.sql.selectAll

object UsersTable : IntIdTable() {

    private val phone = varchar("phone", 20).nullable()
    private val createdAt = datetime("created_at").defaultExpression(CurrentDateTime)

    fun insert(userDto: UserDto) {
        UsersTable.insert {
            it[phone] = userDto.phone
        }
    }

    fun fetch(req: UsersReq): List<UserDto> {
        var page = req.pageNumber
        if (page < 1) page = 1
        val offset = (page - 1) * req.pageSize
        val query = selectAll()
        query.offset(offset.toLong())
        query.limit(req.pageSize)

        return query.map {
            UserDto(
                id = it[id].value,
                phone = it[phone],
                createdAt = it[createdAt]
            )
        }
    }

    fun fetchById(id: Int): UserDto? {
        return UsersTable
            .selectAll()
            .where {
                UsersTable.id eq id
            }.singleOrNull()
            ?.let {
                UserDto(
                    id = it[this.id].value,
                    phone = it[phone],
                    createdAt = it[createdAt]
                )
            }
    }

    fun fetchByPhone(phone: String): UserDto? {
        return UsersTable
            .selectAll()
            .where {
                UsersTable.phone eq phone
            }.singleOrNull()
            ?.let {
                UserDto(
                    id = it[id].value,
                    phone = it[this.phone],
                    createdAt = it[createdAt]
                )
            }
    }

    fun delete(id: Int) {
        deleteWhere {
            UsersTable.id eq id
        }
    }
}