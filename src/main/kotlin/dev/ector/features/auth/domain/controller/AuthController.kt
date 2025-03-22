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
import dev.ector.features.roles.domain.models.Role
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
            repo.deletePhoneCode(req.phone)
            if (codeDto == null || codeDto.code != req.code) {
                throw WrongCodeException()
            }
            val user = usersRepo.fetchByPhone(req.phone)
                ?: usersRepo.create(User(phone = req.phone))
            val access = jwtService.createJwtToken(
                user.id!!.toString(),
                minutes = 600,
                roles = setOf(Role.user, Role.admin)
            )
            val refreshJwt = jwtService.createJwtToken(
                user.id.toString(),
                minutes = 60 * 24 * 14, // 14 days
                roles = setOf(Role.user, Role.admin)
            )
            val refresh = repo.saveRefreshToken(
                RefreshToken(
                    userId = user.id,
                    token = refreshJwt
                )
            ).token
            Tokens(access, refresh)
        }
    }

    override suspend fun createPhoneCode(phone: String) {
        transaction(postgres.db) {
            repo.createPhoneCode(phone)
        }

    }

    override suspend fun renewTokens(token: String): Tokens? {
        val validated = jwtService.validateToken(token)
        if (!validated) {
            val userId = jwtService.extractUserId(token)?.toIntOrNull()
            if (userId != null) {
                transaction(postgres.db) {
                    repo.deleteRefreshToken(userId)
                }
            }
            return null
        }
        val userId = jwtService.extractUserId(token) ?: return null
        val access = jwtService.createJwtToken(
            userId,
            minutes = 600,
            roles = setOf(Role.user)
        )
        val refresh = jwtService.createJwtToken(
            userId,
            minutes = 60 * 24 * 14, // 14 days
            roles = setOf(Role.user)
        )
        val newToken = transaction(postgres.db) {
            repo.replaceRefreshToken(token, refresh)
        }
        return newToken?.let {
            return Tokens(access, refresh)
        }
    }
}