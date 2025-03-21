package dev.ector.features.dict.car_dict.domain.models.model

import dev.ector.features._shared.extensions.F
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CarModel(
    @SerialName(F.ID) val id: Int,
    @SerialName(F.BRAND_ID) val brandId: Int,
    @SerialName(F.NAME) val name: String,
)