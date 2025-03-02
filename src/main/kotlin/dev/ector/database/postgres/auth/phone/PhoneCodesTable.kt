package dev.ector.database.postgres.auth.phone

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentDateTime
import org.jetbrains.exposed.sql.kotlin.datetime.datetime
import org.jetbrains.exposed.sql.selectAll

object PhoneCodesTable : IntIdTable() {
    private val phone = varchar("desc", 20)
    private val code = varchar("code", 6)
    private val createdAt = datetime("created_at").defaultExpression(CurrentDateTime)

    fun insert(dto: PhoneCodeDto) {
        insert {
            it[phone] = dto.phone
            it[code] = dto.code
        }
    }

    fun fetch(phone: String): PhoneCodeDto? {
        return PhoneCodesTable
            .selectAll()
            .where { PhoneCodesTable.phone eq phone }
            .singleOrNull()
            ?.let {
                PhoneCodeDto(
                    id = it[id].value,
                    phone = it[this.phone],
                    code = it[code],
                    createdAt = it[createdAt]
                )
            }

    }

    fun delete(phone: String) {
        deleteWhere {
            PhoneCodesTable.phone eq phone
        }
    }

}