package com.masqx.weatherapp.feature.weather.presentation.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.masqx.weatherapp.core.theme.AppTheme
import com.masqx.weatherapp.core.theme.AppThemeTokens
import com.masqx.weatherapp.core.ui.AnimatedCounter
import com.masqx.weatherapp.feature.weather.domain.CurrentWeather
import com.masqx.weatherapp.feature.weather.domain.DailyForecast
import com.masqx.weatherapp.feature.weather.domain.Degree
import com.masqx.weatherapp.feature.weather.domain.WeatherCode
import kotlinx.datetime.LocalDate

/** Шапка экрана города: большая температура, описание, чипы Макс/Мин, "ощущается как". */
@Composable
fun CurrentWeatherSection(
    current: CurrentWeather,
    today: DailyForecast?,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = AppThemeTokens.spacing.md),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "СЕЙЧАС",
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.primary,
        )

        current.temperature?.let { temperature ->
            AnimatedCounter(targetValue = temperature.celsius) { animatedCelsius ->
                Text(
                    text = "$animatedCelsius°",
                    style = MaterialTheme.typography.displayLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }
        }

        current.weatherCode?.let { code ->
            Text(
                text = weatherDescriptionFor(code),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }

        if (today != null) {
            Spacer(Modifier.height(AppThemeTokens.spacing.sm))
            Row(horizontalArrangement = Arrangement.spacedBy(AppThemeTokens.spacing.sm)) {
                TemperatureChip(
                    text = "Макс: ${today.temperatureMax.celsius}°",
                    emphasized = true,
                )
                TemperatureChip(
                    text = "Мин: ${today.temperatureMin.celsius}°",
                    emphasized = false,
                )
            }
        }

        current.apparentTemperature?.let { feelsLike ->
            Spacer(Modifier.height(AppThemeTokens.spacing.sm))
            Text(
                text = "Ощущается как ${feelsLike.celsius}°",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

@Composable
private fun TemperatureChip(text: String, emphasized: Boolean) {
    val background = if (emphasized) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.surfaceVariant
    }
    val contentColor = if (emphasized) {
        MaterialTheme.colorScheme.onPrimary
    } else {
        MaterialTheme.colorScheme.onSurfaceVariant
    }

    Text(
        text = text,
        style = MaterialTheme.typography.labelMedium,
        color = contentColor,
        modifier = Modifier
            .clip(AppThemeTokens.shapes.full)
            .background(background)
            .padding(horizontal = 12.dp, vertical = 6.dp),
    )
}

@Preview
@Composable
private fun CurrentWeatherSectionPreview() {
    AppTheme {
        CurrentWeatherSection(
            current = WeatherDetailPreviewData.currentWeather,
            today = DailyForecast(
                date = LocalDate(2026, 7, 17),
                weatherCode = WeatherCode.MAINLY_CLEAR,
                temperatureMin = Degree(14),
                temperatureMax = Degree(21),
            ),
        )
    }
}
