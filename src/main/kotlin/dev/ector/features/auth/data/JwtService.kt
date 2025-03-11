package dev.ector.features.auth.data

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import dev.ector.database.postgres.PostgresDb
import dev.ector.features._shared.AppConfig
import dev.ector.features.users.domain.interfaces.IUsersRepo
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class JwtService(
    private val userRepo: IUsersRepo,
    private val postgres: PostgresDb,
    appConfig: AppConfig
) {

    private val secret = appConfig.jwtSecret
    private val issuer = appConfig.jwtIssuer
    private val audience = appConfig.jwtAudience
    val realm = appConfig.jwtRealm

    val jwtVerifier: JWTVerifier =
        JWT
            .require(Algorithm.HMAC256(appConfig.jwtSecret))
            .withAudience(audience)
            .withIssuer(issuer)
            .build()

    fun createJwtToken(userId: String): String {
        return JWT.create()
            .withAudience(audience)
            .withIssuer(issuer)
            .withClaim("user_id", userId)
            .withExpiresAt(Date(System.currentTimeMillis() + 3_600_000 / 4)) // 15 minutes
            .sign(Algorithm.HMAC256(secret))
    }

    fun customValidator(credential: JWTCredential): JWTPrincipal? {
        val userId: String? = extractUserId(credential)
        return userId?.toIntOrNull()?.let {
            return transaction<JWTPrincipal?>(postgres.db) {
                val userFound = userRepo.fetchById(userId.toInt())
                userFound?.let {
                    if (audienceMatches(credential))
                        JWTPrincipal(credential.payload)
                    else
                        null
                }
            }
        }


    }

    private fun audienceMatches(
        credential: JWTCredential,
    ): Boolean =
        credential.payload.audience.contains(audience)


    private fun extractUserId(credential: JWTCredential): String? =
        credential.payload.getClaim("user_id").asString()

}


private val ApplicationCall.principalUserId: String?
    get() = principal<JWTPrincipal>()
        ?.payload
        ?.getClaim("user_id")
        ?.asString()