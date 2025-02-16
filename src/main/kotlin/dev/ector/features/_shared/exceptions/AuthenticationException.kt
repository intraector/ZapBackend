package dev.ector.features._shared.exceptions


class AuthenticationException(
    message: String?,
    cause: Throwable? = null
) :
    Exception(message, cause)