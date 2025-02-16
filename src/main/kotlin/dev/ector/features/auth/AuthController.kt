package dev.ector.features.auth

import dev.ector.database.postgres.tokens.TokenDto
import dev.ector.database.postgres.tokens.Tokens
import dev.ector.database.postgres.users.UserDto
import dev.ector.database.postgres.users.Users
import dev.ector.features.auth.reg.models.RegReq
import dev.ector.features.auth.reg.models.RegResp
import dev.ector.features.auth.sign_in.models.SigninReq
import dev.ector.features.auth.sign_in.models.SigninResp
import io.ktor.http.HttpStatusCode
import io.ktor.server.response.respond
import io.ktor.server.routing.RoutingCall
import java.util.UUID

class AuthController {
    suspend fun register(call: RoutingCall, req: RegReq) {
        val userFound = Users.fetch(req.account)
        if (userFound != null) {
            call.respond(HttpStatusCode.Conflict, "Account already exists")
            return;
        }
        Users.insert(
            UserDto(
                account = req.account,
                password = req.password,
                name = req.name,
                email = null
            )
        )
        val token = createTokenFor(req.account)
        call.respond(HttpStatusCode.OK, RegResp(token))
    }

    suspend fun signIn(call: RoutingCall, req: SigninReq) {
        val userFound = Users.fetch(req.account)
        if (userFound == null || userFound.password != req.password) {
            call.respond(HttpStatusCode.BadRequest, "Account or password is wrong")
            return;
        }
        val token = createTokenFor(req.account)

        call.respond(HttpStatusCode.OK, SigninResp(token))

    }

    private fun createTokenFor(account: String): String {
        val token =  UUID.randomUUID().toString()
        Tokens.insert(
            TokenDto(
                account = account,
                token = token
            )
        )

        return token
    }
}