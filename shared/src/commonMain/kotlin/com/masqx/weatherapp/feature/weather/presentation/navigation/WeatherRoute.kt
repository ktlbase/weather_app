package com.masqx.weatherapp.feature.weather.presentation.navigation

import com.masqx.weatherapp.feature.weather.domain.WeatherDaily

/** Маршруты фичи weather: список городов -> детали конкретного города по [cityId]. */
sealed interface WeatherRoute {
    val route: String

    data object CityList : WeatherRoute {
        override val route = "city_list"
    }

    data object CityDetail : WeatherRoute {
        const val ARG_CITY_ID = "cityId"
        override val route = "city_detail/{$ARG_CITY_ID}"
        fun createRoute(cityId: String) = "city_detail/$cityId"
    }
}