package com.masqx.weatherapp.feature.weather.data.source.local.impl

import com.masqx.weatherapp.feature.weather.data.source.local.`interface`.WeatherLocalDataSource
import com.masqx.weatherapp.feature.weather.domain.CityWeatherSummary
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

/** In-memory кэш. Заменить на SQLDelight/DataStore при появлении реальной персистентности. */
class WeatherLocalDataSourceImpl : WeatherLocalDataSource {
    private val state: MutableStateFlow<List<CityWeatherSummary>> = MutableStateFlow(emptyList())

    override fun observeCitiesShort(): Flow<List<CityWeatherSummary>> = state

    override suspend fun saveCitiesShort(items: List<CityWeatherSummary>) {
        state.value = items
    }
}
