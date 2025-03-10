package dev.ector.features.zaps

import dev.ector.database.postgres.PostgresDb
import dev.ector.features._shared.AppConfig
import dev.ector.features.zaps.data.ZapsRepo
import dev.ector.features.zaps.domain.controller.ZapController
import dev.ector.features.zaps.domain.interfaces.IZapController
import org.koin.core.module.Module
import org.koin.dsl.module

val zapsModule: Module = module {
    single<IZapController> {
        ZapController(
            ZapsRepo(),
            get<AppConfig>().address,
            get<PostgresDb>(),
        )
    }
}