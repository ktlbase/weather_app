package com.masqx.weatherapp.feature.weather.data.mapper

import com.masqx.weatherapp.feature.weather.data.dto.CurrentWeatherDto
import com.masqx.weatherapp.feature.weather.domain.CurrentWeather
import com.masqx.weatherapp.feature.weather.domain.Degree
import com.masqx.weatherapp.feature.weather.domain.WeatherCode

fun CurrentWeatherDto.toDomain(): CurrentWeather = CurrentWeather(
    temperature = temperature2m?.toInt()?.let { Degree(it) },
    relativeHumidityPercent = relativeHumidity2m,
    apparentTemperature = apparentTemperature?.toInt()?.let { Degree(it) },
    isDay = isDay?.let { it != 0 },
    precipitationMm = precipitation,
    rainMm = rain,
    showersMm = showers,
    snowfallCm = snowfall,
    weatherCode = weatherCode?.let { WeatherCode.fromCode(it) },
    cloudCoverPercent = cloudCover,
    pressureMslHpa = pressureMsl,
    surfacePressureHpa = surfacePressure,
    windSpeedKmh = windSpeed10m,
    windDirectionDegrees = windDirection10m,
    windGustsKmh = windGusts10m,
)
