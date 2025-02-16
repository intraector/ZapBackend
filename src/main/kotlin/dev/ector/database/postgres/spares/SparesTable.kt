package dev.ector.database.postgres.spares

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.selectAll
import org.postgresql.core.Oid.VARCHAR

object SparesTable : IntIdTable() {
    private val zapId = integer("brand_id")
    private val description = varchar("desc", 1000)
    private val photos = array<String>("region_id", VARCHAR)

    fun insert(spares: List<SpareDto>): List<Int> {
        val ids = batchInsert(spares) { spare ->
            this[zapId] = spare.zapId
            this[description] = spare.description
            this[photos] = spare.photos
        }.map { it[id] }
        return ids.map { it.value }
    }

    fun fetch(zapIds: List<Int>): List<SpareDto> {
        val query = selectAll().where {
            SparesTable.zapId.inList(zapIds)
        }

        return query.map {
            SpareDto(
                id = it[id].value,
                zapId = it[SparesTable.zapId],
                description = it[description],
                photos = it[photos]
            )
        }
    }

    fun delete(zapId: Int) {
        deleteWhere {
            SparesTable.zapId.eq(zapId)
        }
    }

}