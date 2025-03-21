package dev.ector.features.users.domain.models

import dev.ector.features._shared.extensions.F
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UsersResp(
    @SerialName(F.PAGE_NUMBER) val pageNumber: Int,
    @SerialName(F.PAGE_SIZE) val pageSize: Int,
    @SerialName(F.NO_MORE_PAGES) val noMorePages: Boolean,
    @SerialName(F.DATA) val data: List<User>
)