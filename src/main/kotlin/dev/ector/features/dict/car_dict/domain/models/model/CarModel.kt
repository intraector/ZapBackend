package dev.ector.features.dict.car_dict.domain.models.model

import dev.ector.features._shared.extensions.FieldName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CarModel(
    @SerialName(FieldName.ID) val id: Int,
    @SerialName(FieldName.BRAND_ID) val brandId: Int,
    @SerialName(FieldName.NAME) val name: String,
)