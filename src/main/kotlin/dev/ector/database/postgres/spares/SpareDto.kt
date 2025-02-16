package dev.ector.database.postgres.spares

data class SpareDto(
    val id: Int,
    val zapId: Int,
    val description: String,
    val photos: List<String>,
)