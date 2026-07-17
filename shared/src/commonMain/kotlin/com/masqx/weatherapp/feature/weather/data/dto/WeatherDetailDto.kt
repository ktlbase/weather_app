package com.masqx.weatherapp.feature.weather.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** Ответ Open-Meteo /forecast с current + hourly + daily для экрана города. */
@Serializable
data class WeatherDetailDto(
    @SerialName("current") val current: CurrentWeatherDto,
    @SerialName("hourly") val hourly: HourlyDto,
    @SerialName("daily") val daily: DailyDto,
)

/** Колоночный формат Open-Meteo: параллельные массивы, индекс — час. */
@Serializable
data class HourlyDto(
    @SerialName("time") val time: List<String>,
    @SerialName("temperature_2m") val temperature2m: List<Double>,
    @SerialName("weather_code") val weatherCode: List<Int>,
    @SerialName("is_day") val isDay: List<Int>,
    @SerialName("dew_point_2m") val dewPoint2m: List<Double> = emptyList(),
    @SerialName("visibility") val visibility: List<Double> = emptyList(),
)

/** Параллельные массивы, индекс — день. */
@Serializable
data class DailyDto(
    @SerialName("time") val time: List<String>,
    @SerialName("weather_code") val weatherCode: List<Int>,
    @SerialName("temperature_2m_max") val temperatureMax: List<Double>,
    @SerialName("temperature_2m_min") val temperatureMin: List<Double>,
    @SerialName("uv_index_max") val uvIndexMax: List<Double?> = emptyList(),
)
