package dev.ector.database.postgres.roles

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.selectAll

object RolesTable : IntIdTable() {
    private val path = varchar("path", 255)
    private val role = integer("role")
    private val label = varchar("label", 127)

    fun insert(items: List<RolePathDto>): List<Int> {
        val ids = batchInsert(items) { item ->
            this[role] = item.role
            this[path] = item.path
            this[label] = item.label
        }.map { it[id] }
        return ids.map { it.value }
    }

    fun fetch(): List<RolePathDto> {
        return selectAll().map {
            RolePathDto(
                role = it[RolesTable.role],
                path = it[path],
                label = it[label]
            )
        }
    }

    fun delete(path: String, role: Int) {
        deleteWhere {
            RolesTable.path.eq(path) and RolesTable.role.eq(role)
        }
    }

    fun deleteRole(role: Int) {
        deleteWhere {
            RolesTable.role.eq(role)
        }
    }

}