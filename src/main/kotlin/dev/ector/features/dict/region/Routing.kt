package dev.ector.features.dict.region

import dev.ector.features._shared.S
import dev.ector.features._shared.div
import dev.ector.features._shared.exceptions.AlreadyExistsException
import dev.ector.features._shared.extensions.F
import dev.ector.features._shared.extensions.andAssert
import dev.ector.features._shared.extensions.param
import dev.ector.features._shared.extensions.requireNonNull
import dev.ector.features.dict.region.domain.inteface.IRegionDictRepo
import dev.ector.features.dict.region.domain.models.Region
import dev.ector.features.roles.checkRoles
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.configureRoutingDictRegion() {
    val repo: IRegionDictRepo by inject()
    routing {
        route(S.apiV1 / S.dict / S.region) {
            get {
                val output = repo.fetch()
                call.respond(HttpStatusCode.OK, output)
            }
            authenticate {
                checkRoles() {
                    get(F.MODEL_ID.param / S.images / F.ID.param) {
                        call.respond(HttpStatusCode.OK)
                    }
                }
            }
        }
        authenticate {
            checkRoles {
                route(S.apiV1 / S.admin / S.dict / S.region) {
                    get {
                        val output = repo.fetchFull()
                        call.respond(HttpStatusCode.OK, output)
                    }

                    post {
                        val req = call.receive<List<Region.Full>>()
                        try {
                            repo.insert(req)
                        } catch (e: AlreadyExistsException) {
                            call.respond(HttpStatusCode.Conflict, "Id already exists: ${e.message}")
                        }
                        call.respond(HttpStatusCode.OK)
                    }

                    delete(F.ID.param) {
                        call.pathParameters
                            .requireNonNull(F.ID)
                            .andAssert { it.toIntOrNull() != null }
                        repo.delete(call.pathParameters[F.ID]!!.toInt())
                        call.respond(HttpStatusCode.OK)
                    }

                    put {
                        val req = call.receive<List<Region.Full>>()
                        repo.update(req)
                        call.respond(HttpStatusCode.OK)
                    }
                }
            }
        }
    }
}