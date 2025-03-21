package dev.ector.features.dict.car_dict.domain.models.generation

import dev.ector.features._shared.extensions.F
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Generation(
    @SerialName(F.ID) val id: Int,
    @SerialName(F.MODEL_ID) val modelId: Int,
    @SerialName(F.NAME) val name: String,
    @SerialName(F.YEAR_FROM) val yearFrom: String?,
    @SerialName(F.YEAR_TO) val yearTo: String?,
)