package dev.ector.features.auth.data

import dev.ector.database.postgres.auth.phone.PhoneCodeDto
import dev.ector.database.postgres.auth.phone.PhoneCodesTable
import dev.ector.database.postgres.tokens.RefreshTokenDto
import dev.ector.database.postgres.tokens.RefreshTokensTable
import dev.ector.features.auth.domain.interfaces.IAuthRepo
import dev.ector.features.auth.domain.models.RefreshToken

class AuthRepo() : IAuthRepo {
    override fun createPhoneCode(phone: String) {
        PhoneCodesTable.insert(
            PhoneCodeDto(
                phone = phone,
                code = "000000"
            ),
        )
    }

    override fun deletePhoneCode(phone: String) {
        PhoneCodesTable.delete(phone)
    }

    override fun saveRefreshToken(token: RefreshToken): RefreshToken {
        val tokenDto = RefreshTokensTable.insert(
            RefreshTokenDto(
                userId = token.userId!!,
                token = token.token
            )
        )
        return RefreshToken(
            id = tokenDto.id,
            token = tokenDto.token,
            createdAt = tokenDto.createdAt
        )
    }

    override fun updateRefreshToken(token: RefreshToken): RefreshToken? {
        val tokenDto = RefreshTokensTable.update(
            RefreshTokenDto(
                token = token.token,
                userId = token.userId!!
            )
        )
        return tokenDto?.let {
            RefreshToken(
                id = tokenDto.id,
                token = tokenDto.token,
                userId = tokenDto.userId,
                createdAt = tokenDto.createdAt
            )
        }
    }

    override fun deleteRefreshToken(userId: Int) {
        RefreshTokensTable.deleteByUserId(userId)
    }

}