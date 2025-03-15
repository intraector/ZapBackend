package dev.ector.features.auth.domain.interfaces

import dev.ector.features.auth.domain.models.RefreshToken

interface IAuthRepo {
     fun createPhoneCode(phone: String)
     fun deletePhoneCode(phone: String)
     fun saveRefreshToken(token: RefreshToken): RefreshToken
     fun replaceRefreshToken(oldToken: String, newToken: String): RefreshToken?
     fun deleteRefreshToken(userId: Int)
}