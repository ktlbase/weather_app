package com.masqx.weatherapp.feature.weather.data.repository

import com.masqx.weatherapp.feature.weather.data.mapper.toDomain
import com.masqx.weatherapp.feature.weather.data.source.local.`interface`.WeatherLocalDataSource
import com.masqx.weatherapp.feature.weather.data.source.remote.WeatherRemoteSource
import com.masqx.weatherapp.feature.city.domain.entity.City
import com.masqx.weatherapp.feature.weather.domain.CityWeatherDetail
import com.masqx.weatherapp.feature.weather.domain.CityWeatherSummary
import com.masqx.weatherapp.feature.weather.domain.WeatherDaily
import com.masqx.weatherapp.feature.weather.domain.repository.WeatherRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow

class WeatherRepositoryImpl(
    private val remoteSource: WeatherRemoteSource,
    private val localDataSource: WeatherLocalDataSource,
) : WeatherRepository {
    override fun observeCitiesShort(): Flow<List<CityWeatherSummary>> =
        localDataSource.observeCitiesShort()

    override suspend fun refreshCitiesShort(cities: List<City>): Result<Unit> =
        try {
            if (cities.isEmpty()) return Result.success(Unit)

            val weather = remoteSource.getCurrentWeatherForLocationList(cities.map { it.location })
                .map { it.toDomain() }
            val fresh = cities.zip(weather) { city, current ->
                CityWeatherSummary(
                    city = city,
                    weatherDaily = WeatherDaily(
                        temperature = requireNotNull(current.temperature) { "temperature_2m missing" },
                        weatherCode = requireNotNull(current.weatherCode) { "weather_code missing" }.code,
                    ),
                )
            }
            localDataSource.saveCitiesShort(fresh)
            Result.success(Unit)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Result.failure(e)
        }

    override suspend fun getWeatherDetail(city: City): Result<CityWeatherDetail> =
        try {
            Result.success(
                remoteSource.getWeatherDetail(city.location).toDomain(city.timezone),
            )
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Result.failure(e)
        }
}
