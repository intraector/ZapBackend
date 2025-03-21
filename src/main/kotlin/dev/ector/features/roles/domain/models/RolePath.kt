package dev.ector.features.roles.domain.models

data class RolePath(
    val id: Int? = null,
    val role: Role,
    val path: String,
)