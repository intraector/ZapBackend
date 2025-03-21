package dev.ector.features.dict.car_dict.domain.models.modification

import dev.ector.features._shared.extensions.F
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Modification(
    @SerialName(F.ID) val id: Int,
    @SerialName(F.MODEL_ID) val modelId: Int,
    @SerialName(F.BODY_ID) val bodyId: Int,
    @SerialName(F.NAME) val name: String,
)