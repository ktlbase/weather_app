package com.masqx.weatherapp.feature.weather.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** Поля nullable: реально приходят только те, что запрошены через [CurrentWeatherParam]. */
@Serializable
data class CurrentWeatherDto(
    @SerialName("temperature_2m") val temperature2m: Double? = null,
    @SerialName("relative_humidity_2m") val relativeHumidity2m: Int? = null,
    @SerialName("apparent_temperature") val apparentTemperature: Double? = null,
    @SerialName("is_day") val isDay: Int? = null,
    @SerialName("precipitation") val precipitation: Double? = null,
    @SerialName("rain") val rain: Double? = null,
    @SerialName("showers") val showers: Double? = null,
    @SerialName("snowfall") val snowfall: Double? = null,
    @SerialName("weather_code") val weatherCode: Int? = null,
    @SerialName("cloud_cover") val cloudCover: Int? = null,
    @SerialName("pressure_msl") val pressureMsl: Double? = null,
    @SerialName("surface_pressure") val surfacePressure: Double? = null,
    @SerialName("wind_speed_10m") val windSpeed10m: Double? = null,
    @SerialName("wind_direction_10m") val windDirection10m: Int? = null,
    @SerialName("wind_gusts_10m") val windGusts10m: Double? = null,
)