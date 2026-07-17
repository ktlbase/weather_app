package com.masqx.weatherapp.core.di

import com.masqx.weatherapp.feature.city.di.cityModule
import com.masqx.weatherapp.feature.weather.di.weatherModule

/** Все Koin-модули приложения. Стартуются вместе в [com.masqx.weatherapp.App]. */
val appModules = listOf(
    coreModule,
    platformModule,
    weatherModule,
    cityModule,
)
