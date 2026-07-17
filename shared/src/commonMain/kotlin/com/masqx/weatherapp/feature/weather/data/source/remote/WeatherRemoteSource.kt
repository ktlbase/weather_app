package com.masqx.weatherapp.feature.weather.data.source.remote

import com.masqx.weatherapp.core.service.network.WeatherNetworkService
import com.masqx.weatherapp.feature.city.domain.entity.Location
import com.masqx.weatherapp.feature.weather.data.dto.CurrentWeatherDto
import com.masqx.weatherapp.feature.weather.data.dto.CurrentWeatherParam
import com.masqx.weatherapp.feature.weather.data.dto.WeatherDetailDto
import com.masqx.weatherapp.feature.weather.data.dto.toQueryValue
import io.ktor.client.call.body
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonObject

private val DEFAULT_PARAMS = setOf(
    CurrentWeatherParam.TEMPERATURE_2M,
    CurrentWeatherParam.WEATHER_CODE,
)

interface WeatherRemoteSource {
    suspend fun getCurrentWeatherForLocation(
        location: Location,
        params: Set<CurrentWeatherParam> = DEFAULT_PARAMS,
    ): CurrentWeatherDto

    suspend fun getCurrentWeatherForLocationList(
        locations: List<Location>,
        params: Set<CurrentWeatherParam> = DEFAULT_PARAMS,
    ): List<CurrentWeatherDto>

    /** Полный прогноз для экрана города: current + hourly (48ч) + daily (7 дней). */
    suspend fun getWeatherDetail(location: Location): WeatherDetailDto
}

class WeatherRemoteSourceImpl(private val network: WeatherNetworkService) : WeatherRemoteSource {

    private val json = Json { ignoreUnknownKeys = true }

    override suspend fun getCurrentWeatherForLocation(
        location: Location,
        params: Set<CurrentWeatherParam>,
    ): CurrentWeatherDto {
        val response = network.get(
            "/forecast", params = mapOf(
                "latitude" to location.latitude.toString(),
                "longitude" to location.longitude.toString(),
                "current" to params.toQueryValue()
            )
        )

        return response.body<JsonObject>().toCurrentWeatherDto()
    }


    override suspend fun getCurrentWeatherForLocationList(
        locations: List<Location>,
        params: Set<CurrentWeatherParam>,
    ): List<CurrentWeatherDto> {
        val response = network.get(
            "/forecast",
            params = mapOf(
                "latitude" to locations.joinToString(",") { it.latitude.toString() },
                "longitude" to locations.joinToString(",") { it.longitude.toString() },
                "current" to params.toQueryValue()
            )
        )

        // Open-Meteo returns a single object (not an array) when only one coordinate pair is requested.
        return when (val data = response.body<JsonElement>()) {
            is JsonArray -> data.map { it.jsonObject.toCurrentWeatherDto() }
            is JsonObject -> listOf(data.toCurrentWeatherDto())
            else -> throw SerializationException("Unexpected weather response shape: $data")
        }
    }

    override suspend fun getWeatherDetail(location: Location): WeatherDetailDto {
        val response = network.get(
            "/forecast",
            params = mapOf(
                "latitude" to location.latitude.toString(),
                "longitude" to location.longitude.toString(),
                "current" to CurrentWeatherParam.entries.toSet().toQueryValue(),
                "hourly" to "temperature_2m,weather_code,is_day,dew_point_2m,visibility",
                "daily" to "weather_code,temperature_2m_max,temperature_2m_min,uv_index_max",
                // Время в hourly/daily приходит уже в локальной зоне города.
                "timezone" to "auto",
                "forecast_days" to "7",
            ),
        )
        return json.decodeFromJsonElement(response.body<JsonObject>())
    }

    private fun JsonObject.toCurrentWeatherDto(): CurrentWeatherDto {
        val currentWeather = this["current"]?.jsonObject
            ?: throw SerializationException("Current weather data is missing in the response")
        return json.decodeFromJsonElement(currentWeather)
    }

}