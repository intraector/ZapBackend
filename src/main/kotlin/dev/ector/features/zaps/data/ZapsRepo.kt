package dev.ector.features.zaps.data

import dev.ector.database.postgres.spares.SparesTable
import dev.ector.database.postgres.zaps.ZapsTable
import dev.ector.features.zaps.data.convert.toSpare
import dev.ector.features.zaps.data.convert.toSpareDto
import dev.ector.features.zaps.data.convert.toZap
import dev.ector.features.zaps.data.convert.toZapDto
import dev.ector.features.zaps.domain.interfaces.IZapsRepo
import dev.ector.features.zaps.domain.models.Zap
import dev.ector.features.zaps.domain.models.ZapsReq
import dev.ector.features.zaps.domain.models.ZapsResp

class ZapsRepo() : IZapsRepo {

    override fun fetch(req: ZapsReq): ZapsResp {
        // Get all zaps
        val zaps = ZapsTable.fetch(req).map { it.toZap() }
        // Get all spares
        val ids = zaps.mapNotNull { it.id }.toList()
        val spares = SparesTable.fetch(ids).map { it.toSpare() }
        // Assign spares to zaps
        zaps.forEach { zap ->
            zap.spares = spares.filter { it.zapId == zap.id }
        }

        return ZapsResp(
            pageNumber = req.pageNumber,
            pageSize = req.pageSize,
            noMorePages = zaps.size < req.pageSize,
            data = zaps,
        )
    }

    override fun create(req: Zap): Zap {
        // Insert zap
        val insertedId = ZapsTable.insert(req.toZapDto())

        // Update zap with inserted id
        val req = req.copy(id = insertedId)
        req.spares.forEach { it.zapId = insertedId }

        // Insert spares
        val sparesIds = SparesTable.insert(
            req.spares.map { it.toSpareDto() }
        )
        // Update spares with inserted ids
        req.spares.forEachIndexed { index, spare ->
            spare.id = sparesIds[index]
        }
        return req
    }

    override fun delete(id: Int) {
        SparesTable.delete(id)
        ZapsTable.delete(id)
    }


}