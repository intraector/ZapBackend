package dev.ector.database.mysql.car_dict.tables

import dev.ector.features.dict.car_dict.domain.models.brand.Brand
import dev.ector.features.dict.car_dict.domain.models.brand.BrandsReq
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.lowerCase
import org.jetbrains.exposed.sql.selectAll

object BrandsTable : Table("car_mark") {

    private val id = integer("id_car_mark")
    private val name = varchar("name", 255)

    fun fetch(req: BrandsReq): List<Brand> {

        var page = req.pageNumber
        if (page < 1) page = 1
        val offset = (page - 1) * req.pageSize

        return BrandsTable.selectAll()
            .where { name.lowerCase().like("%${req.query.lowercase()}%") }
            .offset(offset.toLong())
            .limit(req.pageSize)
            .map {
                Brand(
                    id = it[id],
                    name = it[name],
                )
            }
    }
}