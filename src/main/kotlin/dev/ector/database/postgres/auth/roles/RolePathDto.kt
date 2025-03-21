package dev.ector.database.postgres.auth.roles

data class RolePathDto(
    val id: Int? = null,
    val role: Int,
    val path: String,
)