package dev.ector.features.roles.domain.interfaces

import dev.ector.features.roles.domain.models.Role
import dev.ector.features.roles.domain.models.RolePath

interface IRolesRepo {
     fun insert(items: List<RolePath>): List<Int>
     fun fetch(role: Role): List<RolePath>
}