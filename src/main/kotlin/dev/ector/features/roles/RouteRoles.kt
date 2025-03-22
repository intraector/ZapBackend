package dev.ector.features.roles

import dev.ector.features.auth.data.JwtService
import dev.ector.features.roles.domain.interfaces.IRolesController
import io.ktor.server.routing.*
import org.koin.java.KoinJavaComponent.inject


fun Route.checkRoles(
    build: Route.() -> Unit
) {
    val controller: IRolesController by inject(IRolesController::class.java)
    val jwtService: JwtService by inject(JwtService::class.java)
    val rolesPlugin = createRolesPlugin(controller, jwtService)
    install(rolesPlugin)
    build()
}