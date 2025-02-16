package dev.ector.features.zaps.domain.interfaces

import dev.ector.features.zaps.domain.models.Zap
import io.ktor.http.content.*

interface IController {
    suspend fun create(data: MultiPartData): Zap
}