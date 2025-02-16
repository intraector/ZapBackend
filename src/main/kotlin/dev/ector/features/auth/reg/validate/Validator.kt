package dev.ector.features.auth.reg.validate

fun String.isValidAccount(): Boolean {
    if (this.isEmpty()) return false
    if (this.length < 6) return false
    return true
}

fun String?.isValidName(): Boolean {
    if (this == null) return true
    return this.isNotEmpty()
}