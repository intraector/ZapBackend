package dev.ector.features.roles.domain.controller

import dev.ector.database.postgres.PostgresDb
import dev.ector.features.roles.domain.interfaces.IRolesController
import dev.ector.features.roles.domain.interfaces.IRolesInMemoryRepo
import dev.ector.features.roles.domain.interfaces.IRolesPersistentRepo
import dev.ector.features.roles.domain.models.Role
import org.jetbrains.exposed.sql.transactions.transaction


class RolesController(
    val postgres: PostgresDb,
    private val inMemoryRepo: IRolesInMemoryRepo,
    private val persistentRepo: IRolesPersistentRepo
) : IRolesController {

    override suspend fun syncInMemoryWithPersistent() {
        // Read from persistent storage
        val rolesInPersistence = transaction(postgres.db) {
            persistentRepo.fetch()
        }

        // Find roles that are in memory but not in persistence
        // These are default values.
        val absentItems = inMemoryRepo.storage.filter { (key, _) ->
            !rolesInPersistence.containsKey(key)
        }

        // Persist absent items
        if (absentItems.isNotEmpty()) {
            transaction(postgres.db) {
                persistentRepo.insert(absentItems.entries)
            }
        }

        // Replace default values with values from persistence
        inMemoryRepo.storage = transaction(postgres.db) {
            persistentRepo.fetch()
        }
    }

    override fun checkPermission(path: String, roles: Array<String>): Boolean {
        println("Checking permission for path: $path")
        val rolesS = roles.mapNotNull {
            println("it: $it")
            Role.fromCode(it.toIntOrNull())
        }
        println("Roles: $rolesS")
        if (rolesS.contains(Role.superuser)) return true
        return inMemoryRepo.storage[path]?.let { roleAndLabel ->
            return rolesS.any {
                it in roleAndLabel.roles
            }
        } ?: false

    }

}