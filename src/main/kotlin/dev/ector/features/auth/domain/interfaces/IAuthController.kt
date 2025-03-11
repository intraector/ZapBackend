package dev.ector.features.auth.domain.interfaces

import dev.ector.features.auth.domain.models.PhoneCodeReq
import dev.ector.features.auth.domain.models.RefreshToken
import dev.ector.features.auth.domain.models.Tokens

interface IAuthController {
    suspend fun signInWithPhone(req: PhoneCodeReq) : Tokens
    suspend fun createPhoneCode(phone: String)
    suspend fun createRefreshToken(userId: Int): RefreshToken
    suspend fun renewTokens(token: String): Tokens?
}