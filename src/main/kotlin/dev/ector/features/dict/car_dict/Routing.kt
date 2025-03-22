package dev.ector.features.dict.car_dict

import dev.ector.features._shared.S
import dev.ector.features._shared.div
import dev.ector.features._shared.extensions.*
import dev.ector.features.auth.data.JwtService
import dev.ector.features.dict.car_dict.domain.interfaces.ICarDictRepo
import dev.ector.features.dict.car_dict.domain.models.body.CarBodiesReq
import dev.ector.features.dict.car_dict.domain.models.brand.BrandsReq
import dev.ector.features.dict.car_dict.domain.models.generation.GenerationsReq
import dev.ector.features.dict.car_dict.domain.models.model.CarModelsReq
import dev.ector.features.dict.car_dict.domain.models.modification.ModificationsReq
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.configureRoutingDictCars() {
    val repo: ICarDictRepo by inject()
    val jwtService: JwtService by inject()
    routing {
        route(S.apiV1 / S.dict / S.cars) {
            authenticate {

                get("" / S.brands) {

                    val req = BrandsReq(
                        pageNumber = call.parameters.pageNumber(),
                        pageSize = call.parameters.pageSize(),
                        query = call.parameters.searchQuery()
                    )
                    val output = repo.fetchBrands(req)
                    call.respond(HttpStatusCode.OK, output)
                }


                get("" / S.brands) {
                    call.parameters
                        .requireNonNull(F.BRAND_ID)
                        .andAssert { it.toIntOrNull() != null }
                    val req = CarModelsReq(
                        pageNumber = call.parameters.pageNumber(),
                        pageSize = call.parameters.pageSize(),
                        query = call.parameters.searchQuery(),
                        brandId = call.parameters[F.BRAND_ID]!!.toInt(),
                    )
                    val output = repo.fetchModels(req)
                    call.respond(HttpStatusCode.OK, output)
                }

                get("" / S.generations) {
                    call.parameters
                        .requireNonNull(F.MODEL_ID)
                        .andAssert { it.toIntOrNull() != null }
                    val req = GenerationsReq(
                        pageNumber = call.parameters.pageNumber(),
                        pageSize = call.parameters.pageSize(),
                        query = call.parameters.searchQuery(),
                        modelId = call.parameters[F.MODEL_ID]!!.toInt(),
                    )
                    val output = repo.fetchGenerations(req)
                    call.respond(HttpStatusCode.OK, output)
                }

                get("" / S.bodies) {
                    call.parameters
                        .requireNonNull(F.MODEL_ID)
                        .andAssert { it.toIntOrNull() != null }

                    val req = CarBodiesReq(
                        pageNumber = call.parameters.pageNumber(),
                        pageSize = call.parameters.pageSize(),
                        query = call.parameters.searchQuery(),
                        modelId = call.parameters[F.MODEL_ID]!!.toInt(),
                        generationId = call.parameters[F.GENERATION_ID]?.toIntOrNull(),
                    )
                    val output = repo.fetchBodies(req)
                    call.respond(HttpStatusCode.OK, output)
                }

                get("" / S.modifications) {
                    call.parameters
                        .requireNonNull(
                            F.MODEL_ID,
                            F.BODY_ID,
                        ).andAssert { it.toIntOrNull() != null }
                    val req = ModificationsReq(
                        pageNumber = call.parameters.pageNumber(),
                        pageSize = call.parameters.pageSize(),
                        query = call.parameters.searchQuery(),
                        modelId = call.parameters[F.MODEL_ID]!!.toInt(),
                        bodyId = call.parameters[F.BODY_ID]!!.toInt(),
                    )
                    val output = repo.fetchModifications(req)
                    call.respond(HttpStatusCode.OK, output)
                }

            }
        }

    }

}