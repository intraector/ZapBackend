package dev.ector.features.dict.car_dict.domain.models.body

import dev.ector.features._shared.extensions.F
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CarBody(
    @SerialName(F.ID) val id: Int,
    @SerialName(F.MODEL_ID) val modelId: Int,
    @SerialName(F.GENERATION_ID) val generationId: Int?,
    @SerialName(F.NAME) val name: String,
)