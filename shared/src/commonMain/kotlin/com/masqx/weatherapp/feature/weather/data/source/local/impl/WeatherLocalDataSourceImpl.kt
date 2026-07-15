package com.masqx.weatherapp.feature.weather.data.source.local.impl

import com.masqx.weatherapp.feature.weather.data.source.local.`interface`.WeatherLocalDataSource
import com.masqx.weatherapp.feature.weather.domain.CityWeatherShortInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

/** In-memory кэш. Заменить на SQLDelight/DataStore при появлении реальной персистентности. */
class WeatherLocalDataSourceImpl : WeatherLocalDataSource {
    private val state: MutableStateFlow<List<CityWeatherShortInfo>> = MutableStateFlow(emptyList())

    override fun observeCitiesShort(): Flow<List<CityWeatherShortInfo>> = state

    override suspend fun saveCitiesShort(items: List<CityWeatherShortInfo>) {
        state.value = items
    }
}
