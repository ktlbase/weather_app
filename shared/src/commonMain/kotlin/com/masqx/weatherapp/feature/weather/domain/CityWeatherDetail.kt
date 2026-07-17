package com.masqx.weatherapp.feature.weather.domain

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime

/** Прогноз на конкретный час. */
data class HourlyForecast(
    val time: LocalDateTime,
    val temperature: Degree,
    val weatherCode: WeatherCode,
    val isDay: Boolean,
)

/** Прогноз на день: код погоды + диапазон температур. */
data class DailyForecast(
    val date: LocalDate,
    val weatherCode: WeatherCode,
    val temperatureMin: Degree,
    val temperatureMax: Degree,
)

/**
 * Полные данные для экрана города: текущая погода, почасовой прогноз (от текущего часа),
 * прогноз по дням и метрики для плиток (точка росы, УФ, видимость).
 */
data class CityWeatherDetail(
    val current: CurrentWeather,
    val hourly: List<HourlyForecast>,
    val daily: List<DailyForecast>,
    val dewPoint: Degree?,
    val uvIndexMax: Double?,
    val visibilityKm: Double?,
)
