package dev.ector.features.dict.car_dict.domain.models.brand

import dev.ector.features._shared.extensions.FieldName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Brand(
    @SerialName(FieldName.ID) val id: Int,
    @SerialName(FieldName.NAME) val name: String,
)