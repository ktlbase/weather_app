package com.masqx.weatherapp.feature.weather.data.dto

/** Поля, доступные в параметре `current` эндпоинта Open-Meteo `/forecast`. */
enum class CurrentWeatherParam(val apiValue: String) {
    TEMPERATURE_2M("temperature_2m"),
    RELATIVE_HUMIDITY_2M("relative_humidity_2m"),
    APPARENT_TEMPERATURE("apparent_temperature"),
    IS_DAY("is_day"),
    PRECIPITATION("precipitation"),
    RAIN("rain"),
    SHOWERS("showers"),
    SNOWFALL("snowfall"),
    WEATHER_CODE("weather_code"),
    CLOUD_COVER("cloud_cover"),
    PRESSURE_MSL("pressure_msl"),
    SURFACE_PRESSURE("surface_pressure"),
    WIND_SPEED_10M("wind_speed_10m"),
    WIND_DIRECTION_10M("wind_direction_10m"),
    WIND_GUSTS_10M("wind_gusts_10m"),
}

/** Собирает значение query-параметра `current` из набора [CurrentWeatherParam]. */
fun Set<CurrentWeatherParam>.toQueryValue(): String = joinToString(",") { it.apiValue }
