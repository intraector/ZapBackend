package dev.ector.features.zaps.data.convert

import dev.ector.database.postgres.zaps.ZapDto
import dev.ector.features.dict.region.domain.models.Region
import dev.ector.features.zaps.domain.models.Zap

fun ZapDto.toZap(): Zap {
    return Zap(
        id = id,
        brandId = brandId,
        modelId = modelId,
        bodyId = bodyId,
        generationId = generationId,
        modificationId = modificationId,
        region = Region.Default(regionId, regionName),
        description = description,
        spares = emptyList(),
    )
}

fun Zap.toZapDto(): ZapDto {
    return ZapDto(
        id = id ?: -1,
        brandId = brandId,
        modelId = modelId,
        bodyId = bodyId,
        generationId = generationId,
        modificationId = modificationId,
        regionId = region.id,
        regionName = region.name,
        description = description,
    )
}