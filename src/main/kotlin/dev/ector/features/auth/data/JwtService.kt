package dev.ector.features.auth.data

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTVerificationException
import com.auth0.jwt.interfaces.Payload
import dev.ector.database.postgres.PostgresDb
import dev.ector.features._shared.AppConfig
import dev.ector.features.roles.domain.models.Role
import dev.ector.features.users.domain.interfaces.IUsersRepo
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

    fun createJwtToken(
        userId: String,
        minutes: Int,
        roles: Set<Role>
    ): String {
        return JWT.create()
            .withAudience(audience)
            .withIssuer(issuer)
            .withClaim("user_id", userId)
            .withArrayClaim("roles", roles.map { it.code }.toTypedArray())
            .withExpiresAt(Date(System.currentTimeMillis() + minutes * 60 * 1000))
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


    fun validateToken(token: String): Boolean {
        try {
            val decodedJWT = jwtVerifier.verify(token)
            if (decodedJWT.expiresAt.before(Date())) {
                throw JWTVerificationException("Token has expired")
            }
            return true
        } catch (_: JWTVerificationException) {
            return false
        }
    }


    private fun audienceMatches(
        credential: JWTCredential,
    ): Boolean =
        credential.payload.audience.contains(audience)


    private fun extractUserId(credential: JWTCredential): String? =
        credential.payload.getClaim("user_id").asString()

    fun extractUserId(token: String): String? {
        try {
            val decodedJWT = jwtVerifier.verify(token)
            return decodedJWT.getClaim("user_id").asString()
        } catch (_: Exception) {
            return null
        }
    }

    fun extractRoles(payload: Payload): Array<String>? {
        return payload.getClaim("roles").asArray(String::class.java)
    }


}