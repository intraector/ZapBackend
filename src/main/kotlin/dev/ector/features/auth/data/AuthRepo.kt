package dev.ector.features.auth.data

import dev.ector.database.postgres.auth.phone.PhoneCodeDto
import dev.ector.database.postgres.auth.phone.PhoneCodesTable
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

    override fun createRefreshToken(userId: Int): RefreshToken {
        val tokenDto = RefreshTokensTable.create(userId)
        return RefreshToken(
            id = tokenDto.id,
            token = tokenDto.token,
            createdAt = tokenDto.createdAt
        )
    }

    override fun renewToken(token: String): RefreshToken? {
        val tokenDto = RefreshTokensTable.renewToken(token)
        return tokenDto?.let {
            RefreshToken(
                id = tokenDto.id,
                token = tokenDto.token,
                userId = tokenDto.userId,
                createdAt = tokenDto.createdAt
            )
        }
    }


}