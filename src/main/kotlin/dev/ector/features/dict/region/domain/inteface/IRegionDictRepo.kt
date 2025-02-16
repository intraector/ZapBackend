package dev.ector.features.dict.region.domain.inteface

import dev.ector.features.dict.region.domain.models.Region

interface IRegionDictRepo {
    suspend fun insert(items: List<Region.Full>)
    suspend fun fetch(): List<Region.Default>
    suspend fun fetchFull(): List<Region.Full>
    suspend fun update(items: List<Region.Full>)
    suspend fun delete(id: Int)

}