package dev.ector.features._shared.validators



val String.isValidPhone: Boolean
    get() {
        val regex = "^[0-9]{11,15}$".toRegex()
        return regex.matches(this)
    }