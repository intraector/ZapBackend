package dev.ector.features.users.domain.models

import dev.ector.features._shared.extensions.FieldName
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    @SerialName(FieldName.ID) val id: Int? = null,
    @SerialName(FieldName.PHONE) val phone: String? = null,
    @SerialName(FieldName.NAME) val name: String? = null,
    @SerialName(FieldName.CREATED_AT) val createdAt: LocalDateTime? = null,

    )