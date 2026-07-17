package com.masqx.weatherapp.core.service.location

import androidx.compose.runtime.Composable

/**
 * Возвращает лямбду "запросить доступ к геолокации". [onResult] получает true, если доступ есть.
 * Android — системный runtime-диалог; iOS — промпт показывает сам CLLocationManager внутри
 * [LocationService.getCurrentLocation], поэтому там сразу true.
 */
@Composable
expect fun rememberLocationPermissionRequest(onResult: (Boolean) -> Unit): () -> Unit
