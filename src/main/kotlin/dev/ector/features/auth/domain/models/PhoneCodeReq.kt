package dev.ector.features.auth.domain.models

import kotlinx.serialization.Serializable


@Serializable
data class PhoneCodeReq(
    val phone: String,
    val code: String,
)