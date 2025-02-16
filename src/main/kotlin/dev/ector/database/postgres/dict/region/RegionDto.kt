package dev.ector.database.postgres.dict.region


sealed class RegionDto {
    public data class Default(
        val id: Int,
        val name: String,
    ) : RegionDto()

    public data class Full(
        val id: Int,
        val name: String,
        val enabled: Boolean,
    ) : RegionDto()
}