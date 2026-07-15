package com.masqx.weatherapp.feature.weather.presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.masqx.weatherapp.core.service.navigation.NavigationService
import com.masqx.weatherapp.feature.city.domain.entity.CityId
import com.masqx.weatherapp.feature.weather.domain.Degree
import com.masqx.weatherapp.feature.weather.domain.WeatherDaily
import com.masqx.weatherapp.feature.weather.domain.defaultCities
import org.koin.compose.koinInject

/// Детали погоды по конкретному городу.

private val mockWeatherDaily = WeatherDaily(temperature = Degree(20), weatherCode = 0)

@Composable
fun CityDetailScreen(
    cityId: String,
    navigationService: NavigationService = koinInject(),
) {
    val city = defaultCities.firstOrNull { it.id == CityId(cityId) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(city?.name.orEmpty()) },
                navigationIcon = {
                    IconButton(onClick = { navigationService.popBackStack() }) {
                        Text("←")
                    }
                },
            )
        },
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp)) {
            Text("${mockWeatherDaily.temperature.celsius}°", style = MaterialTheme.typography.displayLarge)
            Text("Код погоды: ${mockWeatherDaily.weatherCode}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}
