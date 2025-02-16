package dev.ector.database.mysql.car_dict.tables

import dev.ector.features.dict.car_dict.domain.models.body.CarBodiesReq
import dev.ector.features.dict.car_dict.domain.models.body.CarBody
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.andWhere
import org.jetbrains.exposed.sql.lowerCase
import org.jetbrains.exposed.sql.selectAll


object CarBodiesTable : Table("car_serie") {

    private val id = integer("id_car_serie")
    private val modelId = integer("id_car_model")
    private val generationId = integer("id_car_generation")
    private val name = varchar("name", 255)

    fun fetch(req: CarBodiesReq): List<CarBody> {

        var page = req.pageNumber
        if (page < 1) page = 1
        val offset = (page - 1) * req.pageSize

        val query = CarBodiesTable.selectAll()
        query.andWhere { modelId eq req.modelId }
        req.generationId?.let { query.andWhere { generationId eq it } }
        query.andWhere { name.lowerCase() like "%${req.query.lowercase()}%" }
        query.offset(offset.toLong())
        query.limit(req.pageSize)

        return query
            .map {
                CarBody(
                    id = it[id],
                    modelId = it[modelId],
                    generationId = it[generationId],
                    name = it[name],

                    )
            }
    }
}