package dev.ector.features.zaps.domain.interfaces

import dev.ector.features.zaps.domain.models.Zap
import dev.ector.features.zaps.domain.models.ZapsReq
import dev.ector.features.zaps.domain.models.ZapsResp

interface IZapsRepo {
  suspend  fun fetch(req: ZapsReq): ZapsResp
  suspend  fun create(req: Zap): Zap
  suspend  fun delete(id: Int)
}