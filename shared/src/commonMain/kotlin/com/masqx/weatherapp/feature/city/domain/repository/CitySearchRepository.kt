package com.masqx.weatherapp.feature.city.domain.repository

import com.masqx.weatherapp.feature.city.domain.entity.City

interface CitySearchRepository {
    suspend fun search(query: String): Result<List<City>>
}