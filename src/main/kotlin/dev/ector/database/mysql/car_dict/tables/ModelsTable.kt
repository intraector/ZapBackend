package dev.ector.database.mysql.car_dict.tables

import dev.ector.features.dict.car_dict.domain.models.model.CarModel
import dev.ector.features.dict.car_dict.domain.models.model.CarModelsReq
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.lowerCase
import org.jetbrains.exposed.sql.selectAll

object ModelsTable : Table("car_model") {

    private val id = integer("id_car_model")
    private val brandId = integer("id_car_mark")
    private val name = varchar("name", 255)

    fun fetch(req: CarModelsReq): List<CarModel> {

        var page = req.pageNumber
        if (page < 1) page = 1
        val offset = (page - 1) * req.pageSize

        return ModelsTable.selectAll()
            .where {
                brandId.eq(req.brandId) and
                        name.lowerCase().like("%${req.query.lowercase()}%")
            }
            .offset(offset.toLong())
            .limit(req.pageSize)
            .map {
                CarModel(
                    id = it[id],
                    brandId = it[brandId],
                    name = it[name],
                )
            }
    }
}