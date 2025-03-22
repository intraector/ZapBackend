package dev.ector.features.roles

import dev.ector.features.auth.data.JwtService
import dev.ector.features.roles.domain.interfaces.IRolesController
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.application.hooks.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.plugins.*
import io.ktor.server.routing.*
import io.ktor.server.routing.RoutingRoot.Plugin.RoutingCallStarted
import io.ktor.util.reflect.*


fun createRolesPlugin(
    controller: IRolesController,
    jwtService: JwtService
) = createRouteScopedPlugin(name = "RolesPlugin") {
    var pathWithMethod: String = ""
    pluginConfig.apply {
        on(MonitoringEvent(RoutingCallStarted)) { call ->
            pathWithMethod = call.toPathWithMethod()
        }

        on(AuthenticationChecked) {
            val principal = it.principal<JWTPrincipal>()
            if (principal == null) {
                it.respond(HttpStatusCode.Forbidden, typeInfo<HttpStatusCode>())
                return@on
            }

            val roles = jwtService.extractRoles(principal.payload)
            println("Roles: $roles")
            if (roles == null) {
                it.respond(HttpStatusCode.Forbidden, typeInfo<HttpStatusCode>())
                return@on
            }
            val hasAccess = controller.checkPermission(pathWithMethod, roles)
            println("Has access: $hasAccess")
            if (!hasAccess) {
                it.respond(HttpStatusCode.Forbidden, typeInfo<HttpStatusCode>())
            }
        }

    }
}

fun RoutingCall.toPathWithMethod(): String {
    val variableSegments = mutableListOf<String>()

    for (current in generateSequence(route) { it.parent }) {
        when (val selector = current.selector) {
            is PathSegmentParameterRouteSelector -> variableSegments.add(selector.toString())
            is PathSegmentConstantRouteSelector -> variableSegments.add(selector.toString())
            is AuthenticationRouteSelector -> {}
            is HttpMethodRouteSelector -> {}
            is RootRouteSelector -> {}
        }
    }
    val method = request.origin.method.value

    return "$method /" + variableSegments
        .reversed()
        .joinToString("/")
}