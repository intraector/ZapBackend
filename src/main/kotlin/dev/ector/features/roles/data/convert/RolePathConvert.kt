package dev.ector.features.roles.data.convert

import dev.ector.database.postgres.roles.RolePathDto
import dev.ector.features.roles.domain.models.Role
import dev.ector.features.roles.domain.models.RolesAndLabel

fun Map.Entry<String, RolesAndLabel>.toDtos() = sequence {
    value.roles.forEach {
        yield(
            RolePathDto(
                path = key,
                role = it.code,
                label = value.label,
            )
        )
    }
}

fun List<RolePathDto>.toModelsMap(): Map<String, RolesAndLabel> {
    return groupBy { it.path }
        .mapValues { (_, items) ->
            RolesAndLabel(
                roles = items.mapNotNull { Role.fromCode(it.role) },
                label = items.first().label,
            )
        }
}