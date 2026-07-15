package com.masqx.weatherapp.feature.city.data.repository

import com.masqx.weatherapp.feature.city.data.mapper.toDomain
import com.masqx.weatherapp.feature.city.data.source.remote.CitySearchRemoteSource
import com.masqx.weatherapp.feature.city.domain.entity.City
import com.masqx.weatherapp.feature.city.domain.repository.CitySearchRepository
import kotlinx.coroutines.CancellationException

class CitySearchRepositoryImpl(
    private val searchApi: CitySearchRemoteSource
) : CitySearchRepository {
    override suspend fun search(query: String): Result<List<City>> {
        try {
            val citiesDto = searchApi.search(query)
            val cities = citiesDto.map { it.toDomain() }
            return Result.success(cities)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }
}