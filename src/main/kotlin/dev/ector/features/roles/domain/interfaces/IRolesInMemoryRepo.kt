package dev.ector.features.roles.domain.interfaces

import dev.ector.features.roles.domain.models.RolesAndLabel

interface IRolesInMemoryRepo {
    var storage: Map<String, RolesAndLabel>
}