package dev.ector.features.dict.car_dict.domain.models.brand

import dev.ector.features._shared.extensions.F
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Brand(
    @SerialName(F.ID) val id: Int,
    @SerialName(F.NAME) val name: String,
)