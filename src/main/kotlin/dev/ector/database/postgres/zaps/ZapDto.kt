package dev.ector.database.postgres.zaps

data class ZapDto(
    val id: Int,
    val brandId: Int,
    val modelId: Int,
    val bodyId: Int,
    val generationId: Int?,
    val modificationId: Int,
    val regionId: Int,
    val regionName: String,
    val description: String,
)