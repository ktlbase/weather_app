package com.masqx.weatherapp.feature.weather.di

import com.masqx.weatherapp.core.service.network.WeatherNetworkService
import com.masqx.weatherapp.feature.weather.data.source.local.`interface`.WeatherLocalDataSource
import com.masqx.weatherapp.feature.weather.data.source.local.impl.WeatherLocalDataSourceImpl
import com.masqx.weatherapp.feature.weather.data.repository.WeatherRepositoryImpl
import com.masqx.weatherapp.feature.weather.data.source.remote.WeatherRemoteSource
import com.masqx.weatherapp.feature.weather.data.source.remote.WeatherRemoteSourceImpl
import com.masqx.weatherapp.feature.weather.domain.repository.WeatherRepository
import com.masqx.weatherapp.feature.weather.presentation.viewmodel.CityDetailViewModel
import com.masqx.weatherapp.feature.weather.presentation.viewmodel.CityListViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

/** DI-модуль фичи weather. Сюда добавлять usecase по мере появления. */
val weatherModule = module {
    single { WeatherNetworkService() }

    single<WeatherRemoteSource> { WeatherRemoteSourceImpl(get()) }
    single<WeatherLocalDataSource> { WeatherLocalDataSourceImpl() }

    single<WeatherRepository> { WeatherRepositoryImpl(get(), get()) }

    viewModel { CityListViewModel(get(), get()) }
    viewModel { params -> CityDetailViewModel(city = params.get(), repository = get()) }
}
