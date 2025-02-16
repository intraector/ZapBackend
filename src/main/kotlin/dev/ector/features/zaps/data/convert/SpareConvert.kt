package dev.ector.features.zaps.data.convert

import dev.ector.database.postgres.spares.SpareDto
import dev.ector.features.zaps.domain.models.Spare

fun SpareDto.toSpare(): Spare {
    return Spare(
        id = id,
        zapId = zapId,
        description = description,
        photos = photos.toMutableList(),
    )
}

fun Spare.toSpareDto(): SpareDto {
    return SpareDto(
        id = -1,
        zapId = zapId ?: -1,
        description = description,
        photos = photos,
    )
}