package com.masqx.weatherapp.feature.weather.data.dataSource.remote.`interface`

import com.masqx.weatherapp.feature.weather.domain.City
import com.masqx.weatherapp.feature.weather.domain.CityWeatherShortInfo

interface WeatherRemoteDataSource {
    suspend fun getCitiesShort(cities: List<City>): List<CityWeatherShortInfo>
}