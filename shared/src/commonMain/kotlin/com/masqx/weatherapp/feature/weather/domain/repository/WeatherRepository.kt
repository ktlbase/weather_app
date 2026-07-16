package com.masqx.weatherapp.feature.weather.domain.repository

import com.masqx.weatherapp.feature.city.domain.entity.City
import com.masqx.weatherapp.feature.weather.domain.CityWeatherSummary
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    /** SSOT: данные всегда идут из локального хранилища, сеть его только пополняет. */
    fun observeCitiesShort(): Flow<List<CityWeatherSummary>>

    /** Тянет свежие данные с сети и сохраняет в локальное хранилище. Ошибка не трогает уже сохранённые данные. */
    suspend fun refreshCitiesShort(cities: List<City>): Result<Unit>
}
