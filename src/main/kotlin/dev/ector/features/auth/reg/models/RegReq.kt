package dev.ector.features.auth.reg.models

import kotlinx.serialization.Serializable

@Serializable
data class RegReq(
    val account: String,
    val password: String,
    val name: String
)