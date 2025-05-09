package dev.ector.database.postgres.users

import kotlinx.datetime.LocalDateTime

data class UserDto(
    val id: Int? = null,
    val phone: String? = null,
    val name: String? = null,
    val createdAt: LocalDateTime? = null,

)