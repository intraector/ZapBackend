package dev.ector.database.postgres.zaps

import dev.ector.features.zaps.domain.models.ZapsReq
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll

object ZapsTable : IntIdTable() {
    private val brandId = integer("brand_id")
    private val modelId = integer("model_id")
    private val bodyId = integer("body_id")
    private val generationId = integer("generation_id").nullable()
    private val modificationId = integer("modification_id")
    private val regionId = integer("region_id")
    private val regionName = varchar("regionName", 255)
    private val desc = varchar("desc", 1000)

    fun insert(dto: ZapDto): Int {
        val id = insert {
            it[brandId] = dto.brandId
            it[modelId] = dto.modelId
            it[bodyId] = dto.bodyId
            it[generationId] = dto.generationId
            it[modificationId] = dto.modificationId
            it[regionId] = dto.regionId
            it[regionName] = dto.regionName
            it[desc] = dto.description
        } get id
        return id.value
    }

    fun fetch(req: ZapsReq): List<ZapDto> {
        var page = req.pageNumber
        if (page < 1) page = 1
        val offset = (page - 1) * req.pageSize
        val query = selectAll()
        query.offset(offset.toLong())
        query.limit(req.pageSize)

        return query.map {
            ZapDto(
                id = it[id].value,
                brandId = it[brandId],
                modelId = it[modelId],
                bodyId = it[bodyId],
                generationId = it[generationId],
                modificationId = it[modificationId],
                regionId = it[regionId],
                description = it[desc],
                regionName = it[regionName]
            )
        }
    }

    fun delete(id: Int) {
        deleteWhere {
            this.id.eq(id)
        }
    }

}