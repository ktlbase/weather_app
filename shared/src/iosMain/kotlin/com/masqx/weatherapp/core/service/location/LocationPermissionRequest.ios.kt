package com.masqx.weatherapp.core.service.location

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState

@Composable
actual fun rememberLocationPermissionRequest(onResult: (Boolean) -> Unit): () -> Unit {
    // Промпт показывает CLLocationManager внутри getCurrentLocation — здесь просто продолжаем.
    val currentOnResult by rememberUpdatedState(onResult)
    return { currentOnResult(true) }
}
