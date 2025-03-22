package dev.ector.database.postgres.roles

data class RolePathDto(
    val role: Int,
    val path: String,
    val label: String,
)