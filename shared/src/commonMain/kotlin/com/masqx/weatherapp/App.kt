package com.masqx.weatherapp

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.masqx.weatherapp.core.di.appModules
import com.masqx.weatherapp.feature.weather.presentation.navigation.WeatherNavHost
import org.koin.compose.KoinApplication

@Composable
@Preview
fun App() {
    KoinApplication(application = { modules(appModules) }) {
        MaterialTheme {
            WeatherNavHost()
        }
    }
}
