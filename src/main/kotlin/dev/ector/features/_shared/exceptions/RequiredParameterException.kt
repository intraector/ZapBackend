package dev.ector.features._shared.exceptions


class RequiredParameterException(
    message: String,
    cause: Throwable? = null
) :
    Exception(message, cause)