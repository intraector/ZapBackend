package dev.ector.features.zaps.domain.models

import dev.ector.features._shared.extensions.FieldName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ZapsResp(
    @SerialName(FieldName.PAGE_NUMBER) val pageNumber: Int,
    @SerialName(FieldName.PAGE_SIZE) val pageSize: Int,
    @SerialName(FieldName.NO_MORE_PAGES) val noMorePages: Boolean,
    @SerialName(FieldName.DATA) val data: List<Zap>
)