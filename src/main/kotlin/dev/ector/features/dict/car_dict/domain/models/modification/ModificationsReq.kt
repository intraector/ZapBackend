package dev.ector.features.dict.car_dict.domain.models.modification

import dev.ector.features._shared.extensions.FieldName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class ModificationsReq(
    @SerialName(FieldName.MODEL_ID) val modelId: Int,
    @SerialName(FieldName.BODY_ID) val bodyId: Int,
    @SerialName(FieldName.SEARCH_QUERY) val query: String = "",
    @SerialName(FieldName.PAGE_NUMBER) val pageNumber: Int = 1,
    @SerialName(FieldName.PAGE_SIZE) val pageSize: Int = 100,
)