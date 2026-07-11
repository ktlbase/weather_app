package com.masqx.weatherapp.core.di

import com.masqx.weatherapp.core.navigation.Navigator
import org.koin.dsl.module

/** DI-модуль core-слоя: общие сервисы, доступные всем фичам (навигация и т.п.). */
val coreModule = module {
    single { Navigator() }
}
