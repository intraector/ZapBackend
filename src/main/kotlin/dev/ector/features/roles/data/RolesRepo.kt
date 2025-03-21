package dev.ector.features.roles.data

import dev.ector.database.postgres.auth.roles.RolePathDto
import dev.ector.database.postgres.auth.roles.RolesTable
import dev.ector.features.roles.domain.interfaces.IRolesRepo
import dev.ector.features.roles.domain.models.Role
import dev.ector.features.roles.domain.models.RolePath

class RolesRepo() : IRolesRepo {
    override fun insert(items: List<RolePath>): List<Int> {
        return RolesTable.insert(
            items.map {
                RolePathDto(
                    role = it.role.code,
                    path = it.path,
                )
            }
        )
    }

    override fun fetch(role: Role): List<RolePath> {
        return RolesTable.fetch(role.code)
            .map {
                RolePath(
                    id = it.id,
                    role = Role.fromCode(it.role)!!,
                    path = it.path,
                )
            }
    }

}