package dev.ector.features._shared

import io.ktor.server.request.*
import io.ktor.server.routing.*

/// S stands for "Segement"
@Suppress("EnumEntryName")
enum class S {
    GET, POST, DELETE, PUT, PATCH,
    apiV1,
    auth,
    sign_in_with_phone,
    cars,
    brands,
    zap,
    images,
    dict,
    region,
    admin,
    phone_code,
    refresh_token;

    operator fun div(other: S): String {
        return "/$name/${other.name}"
    }

    operator fun div(other: String): String {
        return "$name/${other}"
    }

    operator fun plus(other: String): String {
        return "$name $other"
    }
}

operator fun String.div(other: S): String {
    return "$this/${other.name}"
}

operator fun String.div(other: String): String {
    return "$this/${other}"
}

val RoutingRequest.pathWithMethod: String
    get() {
        val rawPath = path()
        val segments = rawPath.trim('/').split('/')
        println("segments: $segments")
        val hasPathParameters = !this.pathVariables.isEmpty()
        println("hasPathParameters: $hasPathParameters")
        println("pathVariables: ${this.pathVariables}")
        return this.httpMethod.value + " " + this.path()
    }