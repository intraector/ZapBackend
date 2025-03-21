package dev.ector.features.roles

import dev.ector.features._shared.S
import dev.ector.features._shared.div
import dev.ector.features._shared.extensions.F
import dev.ector.features._shared.extensions.param
import dev.ector.features.auth.data.JwtService
import io.ktor.server.application.*
import io.ktor.server.application.hooks.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.plugins.*
import io.ktor.server.routing.*
import io.ktor.server.routing.RoutingRoot.Plugin.RoutingCallStarted


//        val jwtService: JwtService by inject()

class PluginConfiguration(val jwtService: JwtService) {}

fun createRbacPlugin(jwtService: JwtService) = createRouteScopedPlugin(
    name = "RbacPlugin",
    createConfiguration = {
        PluginConfiguration(
            jwtService = jwtService
        )
    },
) {
    var pathWithMethod: String = ""
    val jwtService = pluginConfig.jwtService
    pluginConfig.apply {
        on(MonitoringEvent(RoutingCallStarted)) { call ->
            pathWithMethod = call.toPathWithMethod()
        }

        on(AuthenticationChecked) {
            val principal = it.principal<JWTPrincipal>()
            println("fullPath: $pathWithMethod")
            val p = S.GET + S.apiV1 / S.dict / S.region / F.MODEL_ID.param / S.images / F.ID.param
            println("p: $p")
            println("fullPath == p : ${pathWithMethod == p}")
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