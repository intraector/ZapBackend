package dev.ector.features.users.data.convert

import dev.ector.database.postgres.users.UserDto
import dev.ector.features.users.domain.models.User


fun UserDto.toUser(): User {
    return User(
        id = id,
        name = name,
        phone = phone,
        createdAt = createdAt,
    )
}

fun User.toUserDto(): UserDto {
    return UserDto(
        name = name,
        phone = phone,
    )
}