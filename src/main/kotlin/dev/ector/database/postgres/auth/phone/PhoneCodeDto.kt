package dev.ector.database.postgres.auth.phone

import kotlinx.datetime.LocalDateTime

data class PhoneCodeDto(
    val id: Int? = null,
    val phone: String,
    val code: String,
    val createdAt: LocalDateTime? = null,
)