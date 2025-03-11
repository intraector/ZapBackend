package dev.ector.features.auth.domain.models

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class RefreshToken(
    val id: Int? = null,
    val token: String,
    val userId: Int? = null,
    val createdAt: LocalDateTime? = null
)