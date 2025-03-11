package dev.ector.features.auth.domain.controller

import dev.ector.database.postgres.PostgresDb
import dev.ector.database.postgres.auth.phone.PhoneCodesTable
import dev.ector.features._shared.exceptions.WrongCodeException
import dev.ector.features.auth.data.JwtService
import dev.ector.features.auth.domain.interfaces.IAuthController
import dev.ector.features.auth.domain.interfaces.IAuthRepo
import dev.ector.features.auth.domain.models.PhoneCodeReq
import dev.ector.features.auth.domain.models.RefreshToken
import dev.ector.features.auth.domain.models.Tokens
import dev.ector.features.users.domain.interfaces.IUsersRepo
import dev.ector.features.users.domain.models.User
import org.jetbrains.exposed.sql.transactions.transaction

class AuthController(
    val postgres: PostgresDb,
    val repo: IAuthRepo,
    val usersRepo: IUsersRepo,
    val jwtService: JwtService
) : IAuthController {

    override suspend fun signInWithPhone(req: PhoneCodeReq): Tokens {
        return transaction(postgres.db) {
            val codeDto = PhoneCodesTable.fetch(req.phone)
            if (codeDto == null || codeDto.code != req.code) {
                throw WrongCodeException()
            }
            repo.deletePhoneCode(req.phone)
            val user = usersRepo.fetchByPhone(req.phone)
                ?: usersRepo.create(User(phone = req.phone))
            val access = jwtService.createJwtToken(user.id!!.toString())
            val refresh = repo.createRefreshToken(user.id).token
            Tokens(access, refresh)
        }
    }

    override suspend fun createPhoneCode(phone: String) {
        transaction(postgres.db) {
            repo.createPhoneCode(phone)
        }

    }

    override suspend fun createRefreshToken(userId: Int): RefreshToken {
        return transaction(postgres.db) {
            repo.createRefreshToken(userId)
        }
    }

    override suspend fun renewTokens(token: String): Tokens? {
        val newToken = transaction(postgres.db) {
            repo.renewToken(token)
        }
        return newToken?.let {
            return Tokens(
                access = jwtService.createJwtToken(it.userId.toString()),
                refresh = it.token
            )
        }
    }
}