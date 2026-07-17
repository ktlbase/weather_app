package com.masqx.weatherapp.feature.weather.domain

/**
 * Текущая погода в точке. Все поля nullable — приходят только те, что были
 * запрошены через [com.masqx.weatherapp.feature.weather.data.dto.CurrentWeatherParam].
 */
data class CurrentWeather(
    val temperature: Degree?,
    val relativeHumidityPercent: Int?,
    val apparentTemperature: Degree?,
    val isDay: Boolean?,
    val precipitationMm: Double?,
    val rainMm: Double?,
    val showersMm: Double?,
    val snowfallCm: Double?,
    val weatherCode: WeatherCode?,
    val cloudCoverPercent: Int?,
    val pressureMslHpa: Double?,
    val surfacePressureHpa: Double?,
    val windSpeedKmh: Double?,
    val windDirectionDegrees: Int?,
    val windGustsKmh: Double?,
)