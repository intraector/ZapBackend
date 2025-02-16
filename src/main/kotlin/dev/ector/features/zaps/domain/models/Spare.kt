package dev.ector.features.zaps.domain.models

import dev.ector.features._shared.extensions.FieldName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Spare(
    @SerialName(FieldName.ID) var id: Int? = -1,
    @SerialName(FieldName.ZAP_ID) var zapId: Int? = -1,
    @SerialName(FieldName.DESCRIPTION) val description: String,
    @SerialName(FieldName.PHOTOS) var photos: MutableList<String>,
)