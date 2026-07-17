package com.masqx.weatherapp.feature.weather.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** Ответ Open-Meteo /forecast для одной точки (при multi-coord запросе приходит массив таких объектов). */
@Serializable
data class WeatherForecastDto(
    @SerialName("current") val current: CurrentWeatherDto,
)

