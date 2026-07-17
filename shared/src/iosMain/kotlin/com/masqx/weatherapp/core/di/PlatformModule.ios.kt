package com.masqx.weatherapp.core.di

import com.masqx.weatherapp.core.service.location.LocationService
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module = module {
    single { LocationService() }
}
