package dev.ector.features.roles.data

import dev.ector.features._shared.S
import dev.ector.features._shared.div
import dev.ector.features._shared.extensions.F
import dev.ector.features._shared.extensions.param
import dev.ector.features.roles.domain.interfaces.IRolesInMemoryRepo
import dev.ector.features.roles.domain.models.Role
import dev.ector.features.roles.domain.models.RolesAndLabel

class RolesInMemoryRepo : IRolesInMemoryRepo {
    override var storage: Map<String, RolesAndLabel> = mapOf(
        S.GET + S.apiV1 / S.admin / S.dict / S.region to RolesAndLabel(
            roles = listOf(Role.admin),
            label = "Регионы: просмотр",
        ),
        S.POST + S.apiV1 / S.admin / S.dict / S.region to RolesAndLabel(
            roles = listOf(Role.admin),
            label = "Регионы: запись",
        ),
        S.DELETE + S.apiV1 / S.admin / S.dict / S.region / F.ID.param to RolesAndLabel(
            roles = listOf(Role.admin),
            label = "Регионы: удаление",
        ),
        S.PUT + S.apiV1 / S.admin / S.dict / S.region to RolesAndLabel(
            roles = listOf(Role.admin),
            label = "Регионы: обновление",
        ),
    )
}