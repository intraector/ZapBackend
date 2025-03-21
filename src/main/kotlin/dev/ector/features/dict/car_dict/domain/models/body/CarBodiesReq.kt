package dev.ector.features.dict.car_dict.domain.models.body

import dev.ector.features._shared.extensions.F
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class CarBodiesReq(
    @SerialName(F.MODEL_ID) val modelId: Int,
    @SerialName(F.GENERATION_ID) val generationId: Int?,
    @SerialName(F.SEARCH_QUERY) val query: String = "",
    @SerialName(F.PAGE_NUMBER) val pageNumber: Int = 1,
    @SerialName(F.PAGE_SIZE) val pageSize: Int = 100,
)