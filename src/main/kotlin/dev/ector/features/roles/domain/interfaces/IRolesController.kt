package dev.ector.features.roles.domain.interfaces


interface IRolesController {
    suspend fun syncInMemoryWithPersistent()
    fun checkPermission(path: String, roles: Array<String>): Boolean
}