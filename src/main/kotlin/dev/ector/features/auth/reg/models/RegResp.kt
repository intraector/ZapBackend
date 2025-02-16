package dev.ector.features.auth.reg.models

import kotlinx.serialization.Serializable

@Serializable
data class RegResp(
    val token: String
)