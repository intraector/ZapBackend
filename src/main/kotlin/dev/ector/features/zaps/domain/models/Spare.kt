package dev.ector.features.zaps.domain.models

import dev.ector.features._shared.extensions.F
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Spare(
    @SerialName(F.ID) var id: Int? = -1,
    @SerialName(F.ZAP_ID) var zapId: Int? = -1,
    @SerialName(F.DESCRIPTION) val description: String,
    @SerialName(F.PHOTOS) var photos: MutableList<String>,
)