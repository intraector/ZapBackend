package dev.ector.features.auth.domain.interfaces

import dev.ector.features.auth.domain.models.RefreshToken

interface IAuthRepo {
     fun createPhoneCode(phone: String)
     fun deletePhoneCode(phone: String)
     fun createRefreshToken(userId: Int): RefreshToken
     fun renewToken(token: String): RefreshToken?
}