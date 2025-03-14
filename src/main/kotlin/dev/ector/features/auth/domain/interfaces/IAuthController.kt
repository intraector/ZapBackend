package dev.ector.features.auth.domain.interfaces

import dev.ector.features.auth.domain.models.PhoneCodeReq
import dev.ector.features.auth.domain.models.Tokens

interface IAuthController {
    suspend fun signInWithPhone(req: PhoneCodeReq): Tokens
    suspend fun createPhoneCode(phone: String)
    suspend fun renewTokens(token: String): Tokens?
}