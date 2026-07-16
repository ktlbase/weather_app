package com.masqx.weatherapp.feature.city.di

import com.masqx.weatherapp.core.service.network.GeocodingNetworkService
import com.masqx.weatherapp.feature.city.data.repository.CitySearchRepositoryImpl
import com.masqx.weatherapp.feature.city.data.source.remote.CitySearchRemoteSource
import com.masqx.weatherapp.feature.city.data.source.remote.CitySearchRemoteSourceImpl
import com.masqx.weatherapp.feature.city.domain.repository.CitySearchRepository
import com.masqx.weatherapp.feature.city.presentation.viewmodel.CitySearchViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

/** DI-модуль фичи city (поиск городов через геокодинг-API). */
val cityModule = module {
    single { GeocodingNetworkService() }

    single<CitySearchRemoteSource> { CitySearchRemoteSourceImpl(get()) }
    single<CitySearchRepository> { CitySearchRepositoryImpl(get(), get()) }

    viewModel { CitySearchViewModel(get()) }
}
