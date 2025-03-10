package dev.ector.features.zaps.domain.interfaces

import dev.ector.features.zaps.domain.models.Zap
import dev.ector.features.zaps.domain.models.ZapsReq
import dev.ector.features.zaps.domain.models.ZapsResp
import io.ktor.http.content.*

interface IZapController {
    suspend fun create(data: MultiPartData): Zap
    suspend fun fetch(req: ZapsReq): ZapsResp
    suspend fun delete(id: Int)
}