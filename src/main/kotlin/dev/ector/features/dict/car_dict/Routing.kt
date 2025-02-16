package dev.ector.features.dict.car_dict

import dev.ector.features._shared.extensions.*
import dev.ector.features.dict.car_dict.domain.interfaces.ICarDictRepo
import dev.ector.features.dict.car_dict.domain.models.body.CarBodiesReq
import dev.ector.features.dict.car_dict.domain.models.brand.BrandsReq
import dev.ector.features.dict.car_dict.domain.models.generation.GenerationsReq
import dev.ector.features.dict.car_dict.domain.models.model.CarModelsReq
import dev.ector.features.dict.car_dict.domain.models.modification.ModificationsReq
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.configureRoutingDictCars() {
    val repo: ICarDictRepo by inject()
    routing {
        route("/api/v1/dict/cars") {
            get("/brands") {
                val req = BrandsReq(
                    pageNumber = call.parameters.pageNumber(),
                    pageSize = call.parameters.pageSize(),
                    query = call.parameters.searchQuery()
                )
                val output = repo.fetchBrands(req)
                call.respond(HttpStatusCode.OK, output)
            }

            get("/models") {
                call.parameters
                    .requireNonNull(FieldName.BRAND_ID)
                    .andAssert { it.toIntOrNull() != null }
                val req = CarModelsReq(
                    pageNumber = call.parameters.pageNumber(),
                    pageSize = call.parameters.pageSize(),
                    query = call.parameters.searchQuery(),
                    brandId = call.parameters[FieldName.BRAND_ID]!!.toInt(),
                )
                val output = repo.fetchModels(req)
                call.respond(HttpStatusCode.OK, output)
            }

            get("/generations") {
                call.parameters
                    .requireNonNull(FieldName.MODEL_ID)
                    .andAssert { it.toIntOrNull() != null }
                val req = GenerationsReq(
                    pageNumber = call.parameters.pageNumber(),
                    pageSize = call.parameters.pageSize(),
                    query = call.parameters.searchQuery(),
                    modelId = call.parameters[FieldName.MODEL_ID]!!.toInt(),
                )
                val output = repo.fetchGenerations(req)
                call.respond(HttpStatusCode.OK, output)
            }

            get("/bodies") {
                call.parameters
                    .requireNonNull(FieldName.MODEL_ID)
                    .andAssert { it.toIntOrNull() != null }

                val req = CarBodiesReq(
                    pageNumber = call.parameters.pageNumber(),
                    pageSize = call.parameters.pageSize(),
                    query = call.parameters.searchQuery(),
                    modelId = call.parameters[FieldName.MODEL_ID]!!.toInt(),
                    generationId = call.parameters[FieldName.GENERATION_ID]?.toIntOrNull(),
                )
                val output = repo.fetchBodies(req)
                call.respond(HttpStatusCode.OK, output)
            }

            get("/modifications") {
                call.parameters
                    .requireNonNull(
                        FieldName.MODEL_ID,
                        FieldName.BODY_ID,
                    ).andAssert { it.toIntOrNull() != null }
                val req = ModificationsReq(
                    pageNumber = call.parameters.pageNumber(),
                    pageSize = call.parameters.pageSize(),
                    query = call.parameters.searchQuery(),
                    modelId = call.parameters[FieldName.MODEL_ID]!!.toInt(),
                    bodyId = call.parameters[FieldName.BODY_ID]!!.toInt(),
                )
                val output = repo.fetchModifications(req)
                call.respond(HttpStatusCode.OK, output)
            }
        }

    }
}