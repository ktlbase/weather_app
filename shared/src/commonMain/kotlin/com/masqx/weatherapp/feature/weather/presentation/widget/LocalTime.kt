package com.masqx.weatherapp.feature.weather.presentation.widget

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.masqx.weatherapp.feature.city.domain.entity.Timezone
import kotlinx.coroutines.delay
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

/** Текущее локальное время города, обновляется раз в минуту. */
@OptIn(ExperimentalTime::class)
@Composable
fun rememberCurrentLocalTime(timezone: Timezone): LocalDateTime {
    var time by remember(timezone) {
        mutableStateOf(Clock.System.now().toLocalDateTime(timezone.toKotlinTimeZone()))
    }
    LaunchedEffect(timezone) {
        while (true) {
            delay(60_000)
            time = Clock.System.now().toLocalDateTime(timezone.toKotlinTimeZone())
        }
    }
    return time
}

/** "15:07". */
fun formatLocalTime(dateTime: LocalDateTime): String {
    val hour = dateTime.hour.toString().padStart(2, '0')
    val minute = dateTime.minute.toString().padStart(2, '0')
    return "$hour:$minute"
}
