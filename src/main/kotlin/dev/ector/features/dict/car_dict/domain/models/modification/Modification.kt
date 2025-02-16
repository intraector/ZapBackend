package dev.ector.features.dict.car_dict.domain.models.modification

import dev.ector.features._shared.extensions.FieldName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Modification(
    @SerialName(FieldName.ID) val id: Int,
    @SerialName(FieldName.MODEL_ID) val modelId: Int,
    @SerialName(FieldName.BODY_ID) val bodyId: Int,
    @SerialName(FieldName.NAME) val name: String,
)