package com.masqx.weatherapp.feature.weather.data.dataSource.remote.impl

import com.masqx.weatherapp.core.service.network.WeatherNetworkService
import com.masqx.weatherapp.feature.weather.data.dataSource.remote.`interface`.WeatherRemoteDataSource
import com.masqx.weatherapp.feature.weather.data.dto.WeatherForecastDto
import com.masqx.weatherapp.feature.weather.domain.City
import com.masqx.weatherapp.feature.weather.domain.CityWeatherShortInfo
import com.masqx.weatherapp.feature.weather.domain.Degree
import com.masqx.weatherapp.feature.weather.domain.WeatherDaily

class WeatherRemoteDataSourceImpl(private val client: WeatherNetworkService) : WeatherRemoteDataSource {
    override suspend fun getCitiesShort(cities: List<City>): List<CityWeatherShortInfo> {
        if (cities.isEmpty()) return emptyList()

        val latitudes = cities.joinToString(",") { it.geoPoint.latitude.toString() }
        val longitudes = cities.joinToString(",") { it.geoPoint.longitude.toString() }

        val forecasts: List<WeatherForecastDto> = client.get(
            path = "/forecast",
            params = mapOf(
                "latitude" to latitudes,
                "longitude" to longitudes,
                "current" to "temperature_2m,weather_code",
            ),
        )

        return cities.zip(forecasts) { city, forecast ->
            CityWeatherShortInfo(
                city = city,
                weatherDaily = WeatherDaily(
                    temperature = Degree(forecast.current.temperature2m.toInt()),
                    weatherCode = forecast.current.weatherCode,
                ),
            )
        }
    }
}
