package dev.ector.features.dict

import dev.ector.features.dict.car_dict.configureRoutingDictCars
import dev.ector.features.dict.region.configureRoutingDictRegion
import io.ktor.server.application.*

fun Application.configureRoutingDict() {
    configureRoutingDictCars()
    configureRoutingDictRegion()
}