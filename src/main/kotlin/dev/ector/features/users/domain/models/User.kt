package dev.ector.features.users.domain.models

import dev.ector.features._shared.extensions.F
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    @SerialName(F.ID) val id: Int? = null,
    @SerialName(F.PHONE) val phone: String? = null,
    @SerialName(F.NAME) val name: String? = null,
    @SerialName(F.CREATED_AT) val createdAt: LocalDateTime? = null,

    )