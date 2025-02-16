package dev.ector.features.auth.sign_in.models

import kotlinx.serialization.Serializable

@Serializable
data class SigninReq(
    val account: String,
    val password: String
)