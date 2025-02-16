package dev.ector.database.postgres.dict.region

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

object RegionTable : Table() {
    val id = integer("id").uniqueIndex()
    private val name = varchar("name", 255)
    private val enabled = bool("enabled")
    override val primaryKey = PrimaryKey(id)

    fun insert(items: List<RegionDto.Full>) {
        batchInsert(items) { region ->
            this[id] = region.id
            this[name] = region.name
            this[enabled] = region.enabled
        }
    }

    fun fetch(): List<RegionDto.Default> {
        val query = selectAll().andWhere { enabled eq true }
        return query.map {
            RegionDto.Default(
                id = it[id],
                name = it[name],
            )
        }
    }

    fun fetchFull(): List<RegionDto.Full> {
        val query = selectAll()
        return query.map {
            RegionDto.Full(
                id = it[id],
                name = it[name],
                enabled = it[enabled]
            )
        }
    }

    fun update(items: List<RegionDto.Full>) {
        items.forEach { region ->
            update({ id eq region.id }) {
                it[id] = region.id
                it[name] = region.name
                it[enabled] = region.enabled
            }
        }
    }

    fun delete(id: Int) {
        deleteWhere { this.id eq id }
    }

}