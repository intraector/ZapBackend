package dev.ector.database.mysql.car_dict.tables

import dev.ector.features.dict.car_dict.domain.models.generation.Generation
import dev.ector.features.dict.car_dict.domain.models.generation.GenerationsReq
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.lowerCase
import org.jetbrains.exposed.sql.selectAll

object GenerationsTable : Table("car_generation") {

    private val id = integer("id_car_generation")
    private val modelId = integer("id_car_model")
    private val name = varchar("name", 255)
    private val yearFrom = varchar("year_begin", 255)
    private val yearTo = varchar("year_end", 255)

    fun fetch(req: GenerationsReq): List<Generation> {

        var page = req.pageNumber
        if (page < 1) page = 1
        val offset = (page - 1) * req.pageSize

        return GenerationsTable.selectAll()
            .where {
                modelId.eq(req.modelId) and
                        name.lowerCase().like("%${req.query.lowercase()}%")
            }
            .offset(offset.toLong())
            .limit(req.pageSize)
            .map {
                Generation(
                    id = it[id],
                    modelId = it[modelId],
                    name = it[name],
                    yearFrom = it[yearFrom],
                    yearTo = it[yearTo],
                )
            }
    }
}