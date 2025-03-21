package dev.ector.features.roles

import dev.ector.features._shared.S
import dev.ector.features._shared.div
import dev.ector.features._shared.extensions.F
import dev.ector.features._shared.extensions.param

class RoutesRoles {
    val data: Map<String, Int> = mapOf(
        S.GET + S.apiV1 / S.dict / S.region to 1,
        S.GET + S.apiV1 / S.dict / S.region / F.MODEL_ID.param / S.images / F.ID.param to 2,
        S.GET + S.apiV1 / S.admin / S.dict / S.region to 3,
        S.POST + S.apiV1 / S.admin / S.dict / S.region to 4,
        S.DELETE + S.apiV1 / S.admin / S.dict / S.region / F.ID.param to 5,
        S.PUT + S.apiV1 / S.admin / S.dict / S.region / F.ID.param to 6,
    )
}