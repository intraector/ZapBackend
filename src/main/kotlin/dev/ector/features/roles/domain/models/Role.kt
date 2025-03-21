package dev.ector.features.roles.domain.models

enum class Role(val code: Int) {
    superuser(0), user(1), admin(2);

    companion object {
        fun fromCode(code: Int): Role? =
            entries.find { it.code == code }
    }
}