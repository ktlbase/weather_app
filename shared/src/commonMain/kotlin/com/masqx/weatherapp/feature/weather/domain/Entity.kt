package com.masqx.weatherapp.feature.weather.domain

import com.masqx.weatherapp.feature.city.domain.entity.City
import kotlin.jvm.JvmInline


/** Температура в цельсиях. Валидирует диапазон [-273, 1000] (абс. ноль .. разумный максимум). */
@JvmInline
value class Degree(val celsius: Int) {
    init {
        require(celsius in -273..1000) {
            "Invalid degree $celsius"
        }
    }
}

/** Погода на день: температура + код погоды (см. WMO, [WeatherCode.code]). */
data class WeatherDaily(
    val temperature: Degree,
    val weatherCode: Int,
)

/** Краткая инфа для списка/виджета: город + его текущая/дневная погода. */
data class CityWeatherShortInfo(
    val city: City,
    val weatherDaily: WeatherDaily,
)
