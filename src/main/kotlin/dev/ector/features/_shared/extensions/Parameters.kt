package dev.ector.features._shared.extensions

import dev.ector.features._shared.exceptions.RequiredParameterException
import io.ktor.http.*

fun Parameters.pageNumber(default: Int = 1): Int {
    var output = this[FieldName.PAGE_NUMBER]?.toIntOrNull() ?: default
    if (output < 1) output = 1
    return output
}

fun Parameters.pageSize(default: Int = 100): Int {
    return this[FieldName.PAGE_SIZE]?.toIntOrNull() ?: default
}

fun Parameters.searchQuery(default: String = ""): String {
    return this[FieldName.SEARCH_QUERY] ?: default
}

fun Parameters.requireNonNull(vararg fields: String): Pair<Set<String>, Parameters> {
    var errorFields = mutableSetOf<String>()
    val output = mutableSetOf<String>()

    for (field in fields) {
        output.add(field)
        if (this[field] == null) {
            errorFields.add(field)
        }
    }

    if (errorFields.isNotEmpty()) {
        throw RequiredParameterException(errorFields.joinToString(", "))
    }

    return Pair(output, this)
}


fun Pair<Set<String>, Parameters>.andAssert(condition: (String) -> Boolean) {
    var errorFields = mutableSetOf<String>()
    for (field in this.first) {
        val met = condition(this.second[field]!!)
        if (!met) {
            errorFields.add(field)
        }
    }

    if (errorFields.isNotEmpty()) {
        throw RequiredParameterException(errorFields.joinToString(", "))
    }
}