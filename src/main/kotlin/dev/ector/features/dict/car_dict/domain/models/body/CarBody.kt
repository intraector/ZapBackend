package dev.ector.features.dict.car_dict.domain.models.body

import dev.ector.features._shared.extensions.FieldName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CarBody(
    @SerialName(FieldName.ID) val id: Int,
    @SerialName(FieldName.MODEL_ID) val modelId: Int,
    @SerialName(FieldName.GENERATION_ID) val generationId: Int?,
    @SerialName(FieldName.NAME) val name: String,
)