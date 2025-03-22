package dev.ector.features._shared

/// S stands for "Segement"
@Suppress("EnumEntryName")
enum class S {
    GET, POST, DELETE, PUT, PATCH,
    apiV1,
    auth,
    sign_in_with_phone,
    cars,
    brands,
    models,
    generations,
    bodies,
    modifications,
    zap,
    zaps,
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