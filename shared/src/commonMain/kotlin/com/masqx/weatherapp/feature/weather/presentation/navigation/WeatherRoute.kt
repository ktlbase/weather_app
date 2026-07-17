package com.masqx.weatherapp.feature.weather.presentation.navigation

import com.masqx.weatherapp.feature.city.domain.entity.City
import io.ktor.http.encodeURLParameter

/** Маршруты фичи weather: список городов -> детали конкретного города по [cityId]. */
sealed interface WeatherRoute {
    val route: String

    data object CityList : WeatherRoute {
        override val route = "city_list"
    }

    data object CityDetail : WeatherRoute {
        const val ARG_CITY_ID = "cityId"
        const val ARG_NAME = "name"
        const val ARG_LAT = "lat"
        const val ARG_LON = "lon"
        const val ARG_TIMEZONE = "tz"
        const val ARG_COUNTRY = "country"
        const val ARG_REGION = "region"

        // Город целиком едет в query-аргументах: результаты поиска нигде не сохранены,
        // восстановить City по одному id на этом экране было бы неоткуда.
        override val route = "city_detail/{$ARG_CITY_ID}" +
            "?$ARG_NAME={$ARG_NAME}&$ARG_LAT={$ARG_LAT}&$ARG_LON={$ARG_LON}" +
            "&$ARG_TIMEZONE={$ARG_TIMEZONE}&$ARG_COUNTRY={$ARG_COUNTRY}&$ARG_REGION={$ARG_REGION}"

        fun createRoute(city: City): String = buildString {
            append("city_detail/${city.id.value.encodeURLParameter()}")
            append("?$ARG_NAME=${city.name.encodeURLParameter()}")
            append("&$ARG_LAT=${city.location.latitude}")
            append("&$ARG_LON=${city.location.longitude}")
            append("&$ARG_TIMEZONE=${city.timezone.id.encodeURLParameter()}")
            append("&$ARG_COUNTRY=${city.country.encodeURLParameter()}")
            // Всегда передаём аргумент (пусть и пустой): у optional query-аргументов без
            // navArgument-дефолтов пропуск параметра ломает матчинг маршрута.
            append("&$ARG_REGION=${city.region.orEmpty().encodeURLParameter()}")
        }
    }

    data object CitySearch : WeatherRoute {
        override val route = "city_search"
    }
}
