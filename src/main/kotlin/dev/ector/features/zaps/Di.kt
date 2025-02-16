package dev.ector.features.zaps

import dev.ector.features.zaps.data.ZapsRepo
import dev.ector.features.zaps.domain.interfaces.IZapsRepo
import org.koin.core.module.Module
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val zapsModule: Module = module {
    singleOf(::ZapsRepo) {
        bind<IZapsRepo>()
    }
}