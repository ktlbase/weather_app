package com.masqx.weatherapp.feature.weather.domain

import kotlin.jvm.JvmInline

/** Город: id — уникальный ключ, name — отображаемое имя, geoPoint — координаты. */
data class City(
    val id: String,
    val name: String,
    val geoPoint: GeoPoint,
)

/** Координаты точки на карте (широта/долгота, градусы). */
data class GeoPoint(
    val latitude: Double,
    val longitude: Double,
)

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
