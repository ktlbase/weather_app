package com.masqx.weatherapp.feature.weather.presentation.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.masqx.weatherapp.core.navigation.Navigator
import com.masqx.weatherapp.feature.weather.domain.CityWeatherShortInfo
import com.masqx.weatherapp.feature.weather.domain.Degree
import com.masqx.weatherapp.feature.weather.domain.WeatherDaily
import com.masqx.weatherapp.feature.weather.domain.defaultCities
import com.masqx.weatherapp.feature.weather.presentation.navigation.WeatherRoute
import org.koin.compose.koinInject

/// Список городов со сводкой на сегодня.

private val mockCityWeatherList: List<CityWeatherShortInfo> = defaultCities.map { city ->
    CityWeatherShortInfo(
        city = city,
        weatherDaily = WeatherDaily(temperature = Degree(20), weatherCode = 0),
    )
}

@Composable
fun CityListScreen(
    navigator: Navigator = koinInject(),
) {
    Scaffold { padding ->
        LazyColumn(modifier = Modifier.fillMaxSize().padding(padding)) {
            items(mockCityWeatherList, key = { it.city.id }) { item ->
                ListItem(
                    headlineContent = { Text(item.city.name) },
                    supportingContent = { Text("${item.weatherDaily.temperature.celsius}°") },
                    modifier = Modifier.clickable {
                        navigator.navigate(WeatherRoute.CityDetail.createRoute(item.city.id))
                    },
                )
            }
        }
    }
}
