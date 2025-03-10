package dev.ector.features.auth.domain.controller

import dev.ector.database.postgres.PostgresDb
import dev.ector.database.postgres.auth.phone.PhoneCodesTable
import dev.ector.features._shared.exceptions.WrongCodeException
import dev.ector.features.auth.domain.interfaces.IAuthController
import dev.ector.features.auth.domain.interfaces.IAuthRepo
import dev.ector.features.auth.domain.models.PhoneCodeReq
import dev.ector.features.users.domain.interfaces.IUsersRepo
import dev.ector.features.users.domain.models.User
import org.jetbrains.exposed.sql.transactions.transaction

class AuthController(
    val postgres: PostgresDb,
    val repo: IAuthRepo,
    val usersRepo: IUsersRepo,
) : IAuthController {

    override suspend fun signInWithPhone(req: PhoneCodeReq) {
        transaction(postgres.db) {
            val codeDto = PhoneCodesTable.fetch(req.phone)
            if (codeDto == null || codeDto.code != req.code) {
                throw WrongCodeException()
            }
            repo.deletePhoneCode(req.phone)
            val user = usersRepo.fetchByPhone(req.phone)
                ?: usersRepo.create(User(phone = req.phone))
        }
    }

    override suspend fun createPhoneCode(phone: String) {
        transaction(postgres.db) {
            repo.createPhoneCode(phone)
        }
    }
}