package dev.ector.features.zaps.domain.interfaces

import dev.ector.features.zaps.domain.models.Zap
import dev.ector.features.zaps.domain.models.ZapsReq
import dev.ector.features.zaps.domain.models.ZapsResp

interface IZapsRepo {
    fun fetch(req: ZapsReq): ZapsResp
    fun create(req: Zap): Zap
    fun delete(id: Int)
}