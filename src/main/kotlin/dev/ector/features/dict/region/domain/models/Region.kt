package dev.ector.features.dict.region.domain.models

import dev.ector.features._shared.extensions.F
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class Region {
    @Serializable
    data class Default(
        @SerialName(F.ID) val id: Int,
        @SerialName(F.NAME) val name: String,
    ) : Region()

    @Serializable
    data class Full(
        @SerialName(F.ID) val id: Int,
        @SerialName(F.NAME) val name: String,
        @SerialName(F.ENABLED) val enabled: Boolean,
    ) : Region()
}