package com.masqx.weatherapp.feature.city.data.repository

import com.masqx.weatherapp.feature.city.data.mapper.toDomain
import com.masqx.weatherapp.feature.city.data.source.remote.CitySearchRemoteSource
import com.masqx.weatherapp.feature.city.domain.repository.CitySearchRepository
import com.masqx.weatherapp.feature.weather.data.dto.CurrentWeatherParam
import com.masqx.weatherapp.feature.weather.data.mapper.toDomain
import com.masqx.weatherapp.feature.weather.data.source.remote.WeatherRemoteSource
import com.masqx.weatherapp.feature.weather.domain.CityCurrentWeather
import kotlinx.coroutines.CancellationException

class CitySearchRepositoryImpl(
    private val searchSource: CitySearchRemoteSource,
    private val weatherSource: WeatherRemoteSource,
) : CitySearchRepository {
    override suspend fun search(query: String): Result<List<CityCurrentWeather>> {
        try {
            val citiesDto = searchSource.search(query)
            val cities = citiesDto.map { it.toDomain() }

            if (cities.isEmpty()) {
                return Result.success(emptyList())
            }

            val locationWeather = weatherSource.getCurrentWeatherForLocationList(
                cities.map { it.location },
                params = CurrentWeatherParam.entries.toSet(),
            ).map { it.toDomain() }

            val cityWeatherList = cities.zip(locationWeather) { city, weather ->
                CityCurrentWeather(city = city, currentWeather = weather)
            }
            return Result.success(cityWeatherList)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }
}