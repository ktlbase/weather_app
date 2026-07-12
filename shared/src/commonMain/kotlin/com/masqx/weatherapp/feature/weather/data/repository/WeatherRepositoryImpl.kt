package com.masqx.weatherapp.feature.weather.data.repository

import com.masqx.weatherapp.feature.weather.data.dataSource.local.`interface`.WeatherLocalDataSource
import com.masqx.weatherapp.feature.weather.data.dataSource.remote.`interface`.WeatherRemoteDataSource
import com.masqx.weatherapp.feature.weather.domain.City
import com.masqx.weatherapp.feature.weather.domain.CityWeatherShortInfo
import com.masqx.weatherapp.feature.weather.domain.repository.WeatherRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow

class WeatherRepositoryImpl(
    private val remoteDataSource: WeatherRemoteDataSource,
    private val localDataSource: WeatherLocalDataSource,
) : WeatherRepository {
    override fun observeCitiesShort(): Flow<List<CityWeatherShortInfo>> =
        localDataSource.observeCitiesShort()

    override suspend fun refreshCitiesShort(cities: List<City>): Result<Unit> =
        try {
            val fresh = remoteDataSource.getCitiesShort(cities)
            localDataSource.saveCitiesShort(fresh)
            Result.success(Unit)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Result.failure(e)
        }
}
