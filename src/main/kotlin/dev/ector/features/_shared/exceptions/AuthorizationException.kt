package dev.ector.features._shared.exceptions


class AuthorizationException(
    message: String?,
    cause: Throwable? = null
) :
    Exception(message, cause)