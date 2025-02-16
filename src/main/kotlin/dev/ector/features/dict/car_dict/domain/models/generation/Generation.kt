package dev.ector.features.dict.car_dict.domain.models.generation

import dev.ector.features._shared.extensions.FieldName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Generation(
    @SerialName(FieldName.ID) val id: Int,
    @SerialName(FieldName.MODEL_ID) val modelId: Int,
    @SerialName(FieldName.NAME) val name: String,
    @SerialName(FieldName.YEAR_FROM) val yearFrom: String?,
    @SerialName(FieldName.YEAR_TO) val yearTo: String?,
)