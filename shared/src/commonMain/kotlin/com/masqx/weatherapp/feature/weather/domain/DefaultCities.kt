package com.masqx.weatherapp.feature.weather.domain

import com.masqx.weatherapp.feature.city.domain.entity.City
import com.masqx.weatherapp.feature.city.domain.entity.CityId
import com.masqx.weatherapp.feature.city.domain.entity.Location
import com.masqx.weatherapp.feature.city.domain.entity.Timezone

val defaultCities = listOf(
    City(id = CityId("moscow"), name = "Moscow", location = Location(55.7558, 37.6173), country = "Russia", timezone = Timezone("Europe/Moscow")),
    City(id = CityId("saint_petersburg"), name = "Saint Petersburg", location = Location(59.9343, 30.3351), country = "Russia", timezone = Timezone("Europe/Moscow")),
    City(id = CityId("london"), name = "London", location = Location(51.5074, -0.1278), country = "United Kingdom", timezone = Timezone("Europe/London")),
    City(id = CityId("paris"), name = "Paris", location = Location(48.8566, 2.3522), country = "France", timezone = Timezone("Europe/Paris")),
    City(id = CityId("berlin"), name = "Berlin", location = Location(52.5200, 13.4050), country = "Germany", timezone = Timezone("Europe/Berlin")),
    City(id = CityId("new_york"), name = "New York", location = Location(40.7128, -74.0060), country = "United States", timezone = Timezone("America/New_York")),
    City(id = CityId("tokyo"), name = "Tokyo", location = Location(35.6762, 139.6503), country = "Japan", timezone = Timezone("Asia/Tokyo")),
    City(id = CityId("beijing"), name = "Beijing", location = Location(39.9042, 116.4074), country = "China", timezone = Timezone("Asia/Shanghai")),
    City(id = CityId("dubai"), name = "Dubai", location = Location(25.2048, 55.2708), country = "United Arab Emirates", timezone = Timezone("Asia/Dubai")),
    City(id = CityId("istanbul"), name = "Istanbul", location = Location(41.0082, 28.9784), country = "Turkey", timezone = Timezone("Europe/Istanbul")),
)
