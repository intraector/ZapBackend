package dev.ector.features.dict.region.data.convert

import dev.ector.database.postgres.dict.region.RegionDto
import dev.ector.features.dict.region.domain.models.Region

fun RegionDto.Default.toRegion(): Region.Default {
    return Region.Default(
        id = id,
        name = name,
    )
}

fun RegionDto.Full.toRegion(): Region.Full {
    return Region.Full(
        id = id,
        name = name,
        enabled = enabled,
    )
}


fun Region.Default.toRegionDto(): RegionDto.Default {
    return RegionDto.Default(
        id = id,
        name = name,
    )
}

fun Region.Full.toRegionDto(): RegionDto.Full {
    return RegionDto.Full(
        id = id,
        name = name,
        enabled = enabled,
    )
}