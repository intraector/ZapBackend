package dev.ector.features.roles.domain.interfaces

import dev.ector.features.roles.domain.models.RolesAndLabel

interface IRolesPersistentRepo{
     fun insert(items: Set<Map.Entry<String, RolesAndLabel>>): List<Int>
     fun fetch(): Map<String, RolesAndLabel>
}