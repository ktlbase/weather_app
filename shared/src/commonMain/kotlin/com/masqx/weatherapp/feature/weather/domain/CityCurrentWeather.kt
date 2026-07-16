package com.masqx.weatherapp.feature.weather.domain

import com.masqx.weatherapp.feature.city.domain.entity.City

/** Результат поиска города: город + вся доступная текущая погода. */
data class CityCurrentWeather(
    val city: City,
    val currentWeather: CurrentWeather?,
)