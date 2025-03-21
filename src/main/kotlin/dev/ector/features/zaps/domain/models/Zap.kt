package dev.ector.features.zaps.domain.models

import dev.ector.features._shared.extensions.F
import dev.ector.features.dict.region.domain.models.Region
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Zap(
    @SerialName(F.ID) val id: Int? = -1,
    @SerialName(F.BRAND_ID) val brandId: Int,
    @SerialName(F.MODEL_ID) val modelId: Int,
    @SerialName(F.BODY_ID) val bodyId: Int,
    @SerialName(F.GENERATION_ID) val generationId: Int?,
    @SerialName(F.MODIFICATION_ID) val modificationId: Int,
    @SerialName(F.REGION) val region: Region.Default,
    @SerialName(F.DESCRIPTION) val description: String,
    @SerialName(F.SPARES) var spares: List<Spare>
)