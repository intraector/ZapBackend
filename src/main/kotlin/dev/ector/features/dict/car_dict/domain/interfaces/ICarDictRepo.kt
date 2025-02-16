package dev.ector.features.dict.car_dict.domain.interfaces

import dev.ector.features.dict.car_dict.domain.models.body.CarBodiesReq
import dev.ector.features.dict.car_dict.domain.models.body.CarBodiesResp
import dev.ector.features.dict.car_dict.domain.models.brand.BrandResp
import dev.ector.features.dict.car_dict.domain.models.brand.BrandsReq
import dev.ector.features.dict.car_dict.domain.models.generation.GenerationsReq
import dev.ector.features.dict.car_dict.domain.models.generation.GenerationsResp
import dev.ector.features.dict.car_dict.domain.models.model.CarModelsReq
import dev.ector.features.dict.car_dict.domain.models.model.CarModelsResp
import dev.ector.features.dict.car_dict.domain.models.modification.ModificationsReq
import dev.ector.features.dict.car_dict.domain.models.modification.ModificationsResp

interface ICarDictRepo {
    suspend fun fetchBrands(req: BrandsReq): BrandResp
    suspend fun fetchModels(req: CarModelsReq): CarModelsResp
    suspend fun fetchGenerations(req: GenerationsReq): GenerationsResp
    suspend fun fetchBodies(req: CarBodiesReq): CarBodiesResp
    suspend fun fetchModifications(req: ModificationsReq): ModificationsResp
}