package com.masqx.weatherapp.feature.weather.presentation.widget

import com.masqx.weatherapp.feature.weather.domain.CityWeatherDetail
import com.masqx.weatherapp.feature.weather.domain.CurrentWeather
import com.masqx.weatherapp.feature.weather.domain.DailyForecast
import com.masqx.weatherapp.feature.weather.domain.Degree
import com.masqx.weatherapp.feature.weather.domain.HourlyForecast
import com.masqx.weatherapp.feature.weather.domain.WeatherCode
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime

/**
 * Мок-данные только для @Preview. Не референсь из prod-кода — держи вызовы этого
 * объекта внутри private-функций, помеченных @Preview.
 */
internal object WeatherDetailPreviewData {
    val currentWeather = CurrentWeather(
        temperature = Degree(17),
        relativeHumidityPercent = 64,
        apparentTemperature = Degree(16),
        isDay = true,
        precipitationMm = 0.0,
        rainMm = 0.0,
        showersMm = 0.0,
        snowfallCm = 0.0,
        weatherCode = WeatherCode.MAINLY_CLEAR,
        cloudCoverPercent = 20,
        pressureMslHpa = 1013.0,
        surfacePressureHpa = 1009.0,
        windSpeedKmh = 12.0,
        windDirectionDegrees = 220,
        windGustsKmh = 18.0,
    )

    val detail = CityWeatherDetail(
        current = currentWeather,
        hourly = (0 until 6).map { index ->
            HourlyForecast(
                time = LocalDateTime(2026, 7, 17, 13 + index, 0),
                temperature = Degree(17 + index),
                weatherCode = if (index < 2) WeatherCode.MAINLY_CLEAR else WeatherCode.PARTLY_CLOUDY,
                isDay = true,
            )
        },
        daily = (0 until 7).map { index ->
            DailyForecast(
                date = LocalDate(2026, 7, 17 + index),
                weatherCode = WeatherCode.entries[index % 4],
                temperatureMin = Degree(13 + index % 3),
                temperatureMax = Degree(21 + index % 4),
            )
        },
        dewPoint = Degree(12),
        uvIndexMax = 4.0,
        visibilityKm = 10.0,
    )
}
