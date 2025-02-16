package dev.ector.features.dict.car_dict.data

import dev.ector.database.mysql.MysqlDb
import dev.ector.database.mysql.car_dict.tables.*
import dev.ector.features.dict.car_dict.domain.interfaces.ICarDictRepo
import dev.ector.features.dict.car_dict.domain.models.body.CarBodiesReq
import dev.ector.features.dict.car_dict.domain.models.body.CarBodiesResp
import dev.ector.features.dict.car_dict.domain.models.body.CarBody
import dev.ector.features.dict.car_dict.domain.models.brand.Brand
import dev.ector.features.dict.car_dict.domain.models.brand.BrandResp
import dev.ector.features.dict.car_dict.domain.models.brand.BrandsReq
import dev.ector.features.dict.car_dict.domain.models.generation.Generation
import dev.ector.features.dict.car_dict.domain.models.generation.GenerationsReq
import dev.ector.features.dict.car_dict.domain.models.generation.GenerationsResp
import dev.ector.features.dict.car_dict.domain.models.model.CarModel
import dev.ector.features.dict.car_dict.domain.models.model.CarModelsReq
import dev.ector.features.dict.car_dict.domain.models.model.CarModelsResp
import dev.ector.features.dict.car_dict.domain.models.modification.Modification
import dev.ector.features.dict.car_dict.domain.models.modification.ModificationsReq
import dev.ector.features.dict.car_dict.domain.models.modification.ModificationsResp
import org.jetbrains.exposed.sql.transactions.transaction

class CarDictRepo(val mysql: MysqlDb) : ICarDictRepo {


    override suspend fun fetchBrands(req: BrandsReq): BrandResp {


        val brands: List<Brand> = transaction(mysql.db) {
            BrandsTable.fetch(req)
        }.map {
            Brand(
                id = it.id,
                name = it.name,
            )
        }
        return BrandResp(
            pageNumber = req.pageNumber,
            pageSize = req.pageSize,
            noMorePages = brands.size < req.pageSize,
            data = brands,
        )

    }

    override suspend fun fetchModels(req: CarModelsReq): CarModelsResp {


        val models: List<CarModel> = transaction(mysql.db) {
            ModelsTable.fetch(req)
        }.map {
            CarModel(
                id = it.id,
                brandId = it.brandId,
                name = it.name,
            )
        }
        return CarModelsResp(
            pageNumber = req.pageNumber,
            pageSize = req.pageSize,
            noMorePages = models.size < req.pageSize,
            data = models,
        )

    }

    override suspend fun fetchGenerations(req: GenerationsReq): GenerationsResp {


        val generations: List<Generation> = transaction(mysql.db) {
            GenerationsTable.fetch(req)
        }.map {
            Generation(
                id = it.id,
                modelId = it.modelId,
                name = it.name,
                yearFrom = it.yearFrom,
                yearTo = it.yearTo,
            )
        }
        return GenerationsResp(
            pageNumber = req.pageNumber,
            pageSize = req.pageSize,
            noMorePages = generations.size < req.pageSize,
            data = generations,
        )

    }

    override suspend fun fetchBodies(req: CarBodiesReq): CarBodiesResp {
        val bodies: List<CarBody> = transaction(mysql.db) {
            CarBodiesTable.fetch(req)
        }.map {
            CarBody(
                id = it.id,
                modelId = it.modelId,
                generationId = it.generationId,
                name = it.name,
            )
        }
        return CarBodiesResp(
            pageNumber = req.pageNumber,
            pageSize = req.pageSize,
            noMorePages = bodies.size < req.pageSize,
            data = bodies,
        )
    }

    override suspend fun fetchModifications(req: ModificationsReq): ModificationsResp {
        val modifications: List<Modification> = transaction(mysql.db) {
            ModificationsTable.fetch(req)
        }.map {
            Modification(
                id = it.id,
                modelId = it.modelId,
                bodyId = it.bodyId,
                name = it.name,
            )
        }
        return ModificationsResp(
            pageNumber = req.pageNumber,
            pageSize = req.pageSize,
            noMorePages = modifications.size < req.pageSize,
            data = modifications,
        )
    }
}