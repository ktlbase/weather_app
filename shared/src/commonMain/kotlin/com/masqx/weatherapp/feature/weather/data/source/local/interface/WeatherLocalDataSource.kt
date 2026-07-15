package com.masqx.weatherapp.feature.weather.data.source.local.`interface`

import com.masqx.weatherapp.feature.weather.domain.CityWeatherShortInfo
import kotlinx.coroutines.flow.Flow

interface WeatherLocalDataSource {
    fun observeCitiesShort(): Flow<List<CityWeatherShortInfo>>
    suspend fun saveCitiesShort(items: List<CityWeatherShortInfo>)
}
