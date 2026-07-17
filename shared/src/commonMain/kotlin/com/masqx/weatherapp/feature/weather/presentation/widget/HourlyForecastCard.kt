package com.masqx.weatherapp.feature.weather.presentation.widget

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.masqx.weatherapp.core.theme.AppTheme
import com.masqx.weatherapp.core.theme.AppThemeTokens
import com.masqx.weatherapp.core.ui.AppCard
import com.masqx.weatherapp.feature.weather.domain.HourlyForecast

/** Карточка одного часа в ленте почасового прогноза: время, иконка, температура. */
@Composable
fun HourlyForecastCard(
    hour: HourlyForecast,
    isNow: Boolean,
    modifier: Modifier = Modifier,
) {
    AppCard(
        modifier = modifier.width(84.dp),
        contentPadding = PaddingValues(
            horizontal = AppThemeTokens.spacing.sm,
            vertical = AppThemeTokens.spacing.md,
        ),
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = if (isNow) "Сейчас" else formatLocalTime(hour.time),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Spacer(Modifier.height(AppThemeTokens.spacing.sm))
            Icon(
                imageVector = weatherIconFor(hour.weatherCode, hour.isDay),
                contentDescription = weatherDescriptionFor(hour.weatherCode),
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(28.dp),
            )
            Spacer(Modifier.height(AppThemeTokens.spacing.sm))
            Text(
                text = "${hour.temperature.celsius}°",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }
    }
}

@Preview
@Composable
private fun HourlyForecastCardPreview() {
    AppTheme {
        HourlyForecastCard(hour = WeatherDetailPreviewData.detail.hourly.first(), isNow = true)
    }
}
