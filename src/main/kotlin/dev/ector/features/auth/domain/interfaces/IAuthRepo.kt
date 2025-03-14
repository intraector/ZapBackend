package dev.ector.features.auth.domain.interfaces

import dev.ector.features.auth.domain.models.RefreshToken

interface IAuthRepo {
     fun createPhoneCode(phone: String)
     fun deletePhoneCode(phone: String)
     fun saveRefreshToken(token: RefreshToken): RefreshToken
     fun updateRefreshToken(token: RefreshToken): RefreshToken?
     fun deleteRefreshToken(userId: Int)
}