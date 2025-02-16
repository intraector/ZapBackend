package dev.ector.features.dict.region.domain.models

import dev.ector.features._shared.extensions.FieldName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class Region {
    @Serializable
    data class Default(
        @SerialName(FieldName.ID) val id: Int,
        @SerialName(FieldName.NAME) val name: String,
    ) : Region()

    @Serializable
    data class Full(
        @SerialName(FieldName.ID) val id: Int,
        @SerialName(FieldName.NAME) val name: String,
        @SerialName(FieldName.ENABLED) val enabled: Boolean,
    ) : Region()
}