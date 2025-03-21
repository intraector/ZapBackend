package dev.ector.database.postgres.auth.roles

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.selectAll

object RolesTable : IntIdTable() {
    private val role = integer("role")
    private val path = varchar("path", 255)

    fun insert(items: List<RolePathDto>): List<Int> {
        val ids = batchInsert(items) { item ->
            this[role] = item.role
            this[path] = item.path
        }.map { it[id] }
        return ids.map { it.value }
    }

    fun fetch(role: Int): List<RolePathDto> {
        val query = selectAll().where {
            RolesTable.role.eq(role)
        }

        return query.map {
            RolePathDto(
                id = it[id].value,
                role = it[RolesTable.role],
                path = it[path],
            )
        }
    }

    fun delete(id: Int) {
        deleteWhere {
            RolesTable.id.eq(id)
        }
    }

}