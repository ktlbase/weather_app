package com.masqx.weatherapp.feature.weather.data.source.local.`interface`

import com.masqx.weatherapp.feature.weather.domain.CityWeatherSummary
import kotlinx.coroutines.flow.Flow

interface WeatherLocalDataSource {
    fun observeCitiesShort(): Flow<List<CityWeatherSummary>>
    suspend fun saveCitiesShort(items: List<CityWeatherSummary>)
}
