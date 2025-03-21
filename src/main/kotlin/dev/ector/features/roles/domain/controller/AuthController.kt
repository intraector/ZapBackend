package dev.ector.features.roles.domain.controller

import dev.ector.database.postgres.PostgresDb
import dev.ector.features.roles.domain.interfaces.IRolesRepo

class RolesController(
    val postgres: PostgresDb,
    val repo: IRolesRepo,
) {

//    suspend fun signInWithPhone(req: PhoneCodeReq): Tokens {
//        return transaction(postgres.db) {
//            val codeDto = PhoneCodesTable.fetch(req.phone)
//            if (codeDto == null || codeDto.code != req.code) {
//                throw WrongCodeException()
//            }
//            repo.deletePhoneCode(req.phone)
//            val user = usersRepo.fetchByPhone(req.phone)
//                ?: usersRepo.create(User(phone = req.phone))
//            val access = jwtService.createJwtToken(
//                user.id!!.toString(),
//                minutes = 15,
//                roles = arrayOf("user")
//            )
//            val refreshJwt = jwtService.createJwtToken(
//                user.id.toString(),
//                minutes = 60 * 24 * 14, // 14 days
//                roles = arrayOf("user")
//            )
//            val refresh = repo.saveRefreshToken(
//                RefreshToken(
//                    userId = user.id,
//                    token = refreshJwt
//                )
//            ).token
//            Tokens(access, refresh)
//        }
//    }


}