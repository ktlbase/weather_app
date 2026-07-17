package com.masqx.weatherapp.feature.weather.presentation.widget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.masqx.weatherapp.core.theme.AppTheme
import com.masqx.weatherapp.core.theme.AppThemeTokens
import com.masqx.weatherapp.core.ui.AppCard
import com.masqx.weatherapp.feature.weather.domain.CityWeatherDetail
import kotlin.math.roundToInt

/** Сетка 2×2 метрик: влажность, ветер, УФ-индекс, видимость. */
@Composable
fun MetricTilesGrid(
    detail: CityWeatherDetail,
    modifier: Modifier = Modifier,
) {
    val current = detail.current

    Column(
        modifier = modifier.padding(horizontal = AppThemeTokens.spacing.md),
        verticalArrangement = Arrangement.spacedBy(AppThemeTokens.spacing.sm),
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(AppThemeTokens.spacing.sm)) {
            MetricTile(
                title = "Влажность",
                value = current.relativeHumidityPercent?.let { "$it%" },
                caption = detail.dewPoint?.let { "Точка росы ${it.celsius}°" },
                modifier = Modifier.weight(1f),
            )
            MetricTile(
                title = "Ветер",
                value = current.windSpeedKmh?.let { "${it.roundToInt()} км/ч" },
                caption = current.windDirectionDegrees?.let { windDirectionName(it) },
                modifier = Modifier.weight(1f),
            )
        }
        Row(horizontalArrangement = Arrangement.spacedBy(AppThemeTokens.spacing.sm)) {
            MetricTile(
                title = "УФ-индекс",
                value = detail.uvIndexMax?.let { "${it.roundToInt()}" },
                caption = detail.uvIndexMax?.let { uvIndexDescription(it) },
                modifier = Modifier.weight(1f),
            )
            MetricTile(
                title = "Видимость",
                value = detail.visibilityKm?.let { "${it.roundToInt()} км" },
                caption = detail.visibilityKm?.let { visibilityDescription(it) },
                modifier = Modifier.weight(1f),
            )
        }
    }
}

@Composable
private fun MetricTile(
    title: String,
    value: String?,
    caption: String?,
    modifier: Modifier = Modifier,
) {
    AppCard(
        modifier = modifier,
        contentPadding = PaddingValues(AppThemeTokens.spacing.md),
    ) {
        Column {
            Text(
                text = title.uppercase(),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Spacer(Modifier.height(AppThemeTokens.spacing.xs))
            Text(
                text = value ?: "—",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSurface,
            )
            if (caption != null) {
                Spacer(Modifier.height(AppThemeTokens.spacing.xs))
                Text(
                    text = caption,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}

private fun windDirectionName(degrees: Int): String {
    val directions = listOf(
        "Северный", "Северо-восточный", "Восточный", "Юго-восточный",
        "Южный", "Юго-западный", "Западный", "Северо-западный",
    )
    val index = ((degrees % 360 + 360) % 360 + 22) / 45 % 8
    return directions[index]
}

private fun uvIndexDescription(uvIndex: Double): String = when {
    uvIndex < 3 -> "Низкий"
    uvIndex < 6 -> "Умеренный"
    uvIndex < 8 -> "Высокий"
    uvIndex < 11 -> "Очень высокий"
    else -> "Экстремальный"
}

private fun visibilityDescription(visibilityKm: Double): String = when {
    visibilityKm >= 10 -> "Отличная"
    visibilityKm >= 5 -> "Хорошая"
    visibilityKm >= 1 -> "Умеренная"
    else -> "Слабая"
}

@Preview
@Composable
private fun MetricTilesGridPreview() {
    AppTheme {
        MetricTilesGrid(detail = WeatherDetailPreviewData.detail)
    }
}
