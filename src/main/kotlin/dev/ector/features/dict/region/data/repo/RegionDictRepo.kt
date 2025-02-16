package dev.ector.features.dict.region.data.repo

import dev.ector.database.postgres.PostgresDb
import dev.ector.database.postgres.dict.region.RegionTable
import dev.ector.features._shared.exceptions.AlreadyExistsException
import dev.ector.features.dict.region.data.convert.toRegion
import dev.ector.features.dict.region.data.convert.toRegionDto
import dev.ector.features.dict.region.domain.inteface.IRegionDictRepo
import dev.ector.features.dict.region.domain.models.Region
import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.jetbrains.exposed.sql.transactions.transaction

class RegionDictRepo(val postgres: PostgresDb) : IRegionDictRepo {
    override suspend fun insert(items: List<Region.Full>) {
        try {
            transaction(postgres.db) {
                RegionTable.insert(items.map { it.toRegionDto() })
            }
        } catch (e: ExposedSQLException) {
            if (e.sqlState == "23505") {
                val message = e.message ?: ""
                val regex = "Key \\(id\\)=\\((\\d+)\\) already exists".toRegex()
                val matchResult = regex.find(message)
                val id = matchResult?.groups?.get(1)?.value?.toIntOrNull()
                throw AlreadyExistsException(id.toString())
            } else throw e
        }
    }

    override suspend fun fetch(): List<Region.Default> {
        return transaction(postgres.db) {
            RegionTable.fetch()
        }.map { it.toRegion() }
    }

    override suspend fun fetchFull(): List<Region.Full> {
        return transaction(postgres.db) {
            RegionTable.fetchFull()
        }.map { it.toRegion() }
    }

    override suspend fun update(items: List<Region.Full>) {
        transaction(postgres.db) {
            RegionTable.update(items.map { it.toRegionDto() })
        }
    }

    override suspend fun delete(id: Int) {
        transaction(postgres.db) {
            RegionTable.delete(id)
        }
    }

}