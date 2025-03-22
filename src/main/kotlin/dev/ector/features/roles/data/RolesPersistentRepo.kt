package dev.ector.features.roles.data

import dev.ector.database.postgres.roles.RolesTable
import dev.ector.features.roles.data.convert.toDtos
import dev.ector.features.roles.data.convert.toModelsMap
import dev.ector.features.roles.domain.interfaces.IRolesPersistentRepo
import dev.ector.features.roles.domain.models.RolesAndLabel

class RolesPersistentRepo() : IRolesPersistentRepo {
    override fun insert(items: Set<Map.Entry<String, RolesAndLabel>>): List<Int> {
        val dtos = items.flatMap { it.toDtos() }
        return RolesTable.insert(dtos)
    }

    override fun fetch(): Map<String, RolesAndLabel> {
        return RolesTable.fetch().toModelsMap()
    }

}