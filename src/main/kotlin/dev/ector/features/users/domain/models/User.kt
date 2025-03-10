package dev.ector.features.users.domain.models

import kotlinx.datetime.LocalDateTime

data class User(
    val id: Int? = null,
    val phone: String? = null,
    val name: String? = null,
    val createdAt: LocalDateTime? = null,

)