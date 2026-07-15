package com.masqx.weatherapp.feature.weather.data.source.remote.`interface`

import com.masqx.weatherapp.feature.city.domain.entity.City
import com.masqx.weatherapp.feature.weather.domain.CityWeatherShortInfo

interface WeatherRemoteDataSource {
    suspend fun getCitiesShort(cities: List<City>): List<CityWeatherShortInfo>
}