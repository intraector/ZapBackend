package dev.ector.features.dict

import dev.ector.features.dict.car_dict.data.CarDictRepo
import dev.ector.features.dict.car_dict.domain.interfaces.ICarDictRepo
import dev.ector.features.dict.region.data.repo.RegionDictRepo
import dev.ector.features.dict.region.domain.inteface.IRegionDictRepo
import org.koin.core.module.Module
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val dictModule: Module = module {
    singleOf(::CarDictRepo) {
        bind<ICarDictRepo>()
    }
    singleOf(::RegionDictRepo) {
        bind<IRegionDictRepo>()
    }
}