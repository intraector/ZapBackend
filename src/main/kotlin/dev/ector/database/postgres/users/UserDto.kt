package dev.ector.database.postgres.users

class UserDto(
    val account: String,
    val password: String,
    val email: String?,
    val name: String
)