package com.masqx.weatherapp.feature.weather.data.source.remote.impl

import com.masqx.weatherapp.core.service.network.WeatherNetworkService
import com.masqx.weatherapp.feature.weather.data.source.remote.`interface`.WeatherRemoteDataSource
import com.masqx.weatherapp.feature.weather.data.dto.WeatherForecastDto
import com.masqx.weatherapp.feature.city.domain.entity.City
import com.masqx.weatherapp.feature.weather.domain.CityWeatherShortInfo
import com.masqx.weatherapp.feature.weather.domain.Degree
import com.masqx.weatherapp.feature.weather.domain.WeatherDaily
import io.ktor.client.call.body

class WeatherRemoteDataSourceImpl(private val client: WeatherNetworkService) : WeatherRemoteDataSource {
    override suspend fun getCitiesShort(cities: List<City>): List<CityWeatherShortInfo> {
        if (cities.isEmpty()) return emptyList()

        val latitudes = cities.joinToString(",") { it.location.latitude.toString() }
        val longitudes = cities.joinToString(",") { it.location.longitude.toString() }

        val forecasts: List<WeatherForecastDto> = client.get(
            path = "/forecast",
            params = mapOf(
                "latitude" to latitudes,
                "longitude" to longitudes,
                "current" to "temperature_2m,weather_code",
            ),
        ).body()

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
