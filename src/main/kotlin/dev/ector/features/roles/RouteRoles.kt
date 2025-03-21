package dev.ector.features.roles

import dev.ector.features.auth.data.JwtService
import io.ktor.server.routing.*
import org.koin.java.KoinJavaComponent.inject


fun Route.checkRoles(
    build: Route.() -> Unit
) {
    val jwtService: JwtService by inject(JwtService::class.java)
    val rbacPlugin = createRbacPlugin(
        jwtService = jwtService
    )
    install(rbacPlugin)
    build()
}