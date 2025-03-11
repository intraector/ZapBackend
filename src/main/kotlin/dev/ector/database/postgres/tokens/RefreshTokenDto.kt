package dev.ector.database.postgres.tokens

import kotlinx.datetime.LocalDateTime

class RefreshTokenDto(
    val id: Int,
    val token: String,
    val userId: Int,
    val createdAt: LocalDateTime
)