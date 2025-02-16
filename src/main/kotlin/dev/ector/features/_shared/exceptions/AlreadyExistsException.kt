package dev.ector.features._shared.exceptions


class AlreadyExistsException(
    message: String? = null,
    cause: Throwable? = null
) :
    Exception(message, cause)