package dev.ector.features.zaps.domain.models

import dev.ector.features._shared.extensions.FieldName
import dev.ector.features.dict.region.domain.models.Region
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Zap(
    @SerialName(FieldName.ID) val id: Int? = -1,
    @SerialName(FieldName.BRAND_ID) val brandId: Int,
    @SerialName(FieldName.MODEL_ID) val modelId: Int,
    @SerialName(FieldName.BODY_ID) val bodyId: Int,
    @SerialName(FieldName.GENERATION_ID) val generationId: Int?,
    @SerialName(FieldName.MODIFICATION_ID) val modificationId: Int,
    @SerialName(FieldName.REGION) val region: Region.Default,
    @SerialName(FieldName.DESCRIPTION) val description: String,
    @SerialName(FieldName.SPARES) var spares: List<Spare>
)