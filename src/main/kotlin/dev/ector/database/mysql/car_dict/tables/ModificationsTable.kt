package dev.ector.database.mysql.car_dict.tables

import dev.ector.features.dict.car_dict.domain.models.modification.Modification
import dev.ector.features.dict.car_dict.domain.models.modification.ModificationsReq
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.andWhere
import org.jetbrains.exposed.sql.lowerCase
import org.jetbrains.exposed.sql.selectAll


object ModificationsTable : Table("car_modification") {

    private val id = integer("id_car_modification")
    private val modelId = integer("id_car_model")
    private val bodyId = integer("id_car_serie")
    private val name = varchar("name", 255)

    fun fetch(req: ModificationsReq): List<Modification> {

        var page = req.pageNumber
        if (page < 1) page = 1
        val offset = (page - 1) * req.pageSize

        val query = ModificationsTable.selectAll()
        query.andWhere { modelId eq req.modelId }
        query.andWhere { bodyId eq req.bodyId }
        query.andWhere { name.lowerCase() like "%${req.query.lowercase()}%" }
        query.offset(offset.toLong())
        query.limit(req.pageSize)

        return query
            .map {
                Modification(
                    id = it[id],
                    modelId = it[modelId],
                    bodyId = it[bodyId],
                    name = it[name],
                )
            }
    }
}