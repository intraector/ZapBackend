package dev.ector.features.auth.sign_in.models

import kotlinx.serialization.Serializable

@Serializable
data class SigninResp(
    val token: String
)