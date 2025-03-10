package dev.ector.features.auth.domain.interfaces

import dev.ector.features.auth.domain.models.PhoneCodeReq

interface IAuthController {
    suspend fun signInWithPhone(req: PhoneCodeReq)
    suspend fun createPhoneCode(phone: String)
}