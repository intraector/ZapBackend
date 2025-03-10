package dev.ector.features._shared.exceptions


class WrongCodeException(
    message: String? = "Wrong code",
    cause: Throwable? = null
) :
    Exception(message, cause)