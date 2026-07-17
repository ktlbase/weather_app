package com.masqx.weatherapp.feature.weather.presentation.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.masqx.weatherapp.core.theme.AppTheme
import com.masqx.weatherapp.core.theme.AppThemeTokens
import com.masqx.weatherapp.core.ui.AppCard
import com.masqx.weatherapp.feature.weather.domain.DailyForecast
import kotlinx.datetime.DayOfWeek

/** Карта прогноза на неделю: строка на день с диапазоном температур внутри недельного. */
@Composable
fun DailyForecastCard(
    daily: List<DailyForecast>,
    modifier: Modifier = Modifier,
) {
    val weekMin = daily.minOf { it.temperatureMin.celsius }
    val weekMax = daily.maxOf { it.temperatureMax.celsius }

    AppCard(
        modifier = modifier,
        contentPadding = PaddingValues(AppThemeTokens.spacing.md),
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(AppThemeTokens.spacing.md)) {
            daily.forEachIndexed { index, day ->
                DailyForecastRow(
                    day = day,
                    label = if (index == 0) "Сегодня" else shortDayName(day.date.dayOfWeek),
                    weekMin = weekMin,
                    weekMax = weekMax,
                )
            }
        }
    }
}

@Composable
private fun DailyForecastRow(
    day: DailyForecast,
    label: String,
    weekMin: Int,
    weekMax: Int,
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = label,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.width(72.dp),
        )
        Icon(
            imageVector = weatherIconFor(day.weatherCode),
            contentDescription = weatherDescriptionFor(day.weatherCode),
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(22.dp),
        )
        Spacer(Modifier.width(AppThemeTokens.spacing.md))
        Text(
            text = "${day.temperatureMin.celsius}°",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.End,
            modifier = Modifier.widthIn(min = 32.dp),
        )
        Spacer(Modifier.width(AppThemeTokens.spacing.sm))
        TemperatureRangeBar(
            rangeMin = day.temperatureMin.celsius,
            rangeMax = day.temperatureMax.celsius,
            weekMin = weekMin,
            weekMax = weekMax,
            modifier = Modifier.weight(1f),
        )
        Spacer(Modifier.width(AppThemeTokens.spacing.sm))
        Text(
            text = "${day.temperatureMax.celsius}°",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.End,
            modifier = Modifier.widthIn(min = 32.dp),
        )
    }
}

/** Полоска диапазона дня внутри общего диапазона недели (как в системных погодных виджетах). */
@Composable
private fun TemperatureRangeBar(
    rangeMin: Int,
    rangeMax: Int,
    weekMin: Int,
    weekMax: Int,
    modifier: Modifier = Modifier,
) {
    val weekSpan = (weekMax - weekMin).coerceAtLeast(1).toFloat()
    val startFraction = ((rangeMin - weekMin) / weekSpan).coerceIn(0f, 1f)
    val endFraction = ((rangeMax - weekMin) / weekSpan).coerceIn(0f, 1f)

    Row(
        modifier = modifier
            .height(6.dp)
            .clip(AppThemeTokens.shapes.full)
            .background(MaterialTheme.colorScheme.surfaceVariant),
    ) {
        if (startFraction > 0f) {
            Spacer(Modifier.weight(startFraction))
        }
        Box(
            modifier = Modifier
                .weight((endFraction - startFraction).coerceAtLeast(0.05f))
                .fillMaxSize()
                .clip(AppThemeTokens.shapes.full)
                .background(MaterialTheme.colorScheme.primary),
        )
        if (endFraction < 1f) {
            Spacer(Modifier.weight(1f - endFraction))
        }
    }
}

private fun shortDayName(dayOfWeek: DayOfWeek): String = when (dayOfWeek) {
    DayOfWeek.MONDAY -> "Пн"
    DayOfWeek.TUESDAY -> "Вт"
    DayOfWeek.WEDNESDAY -> "Ср"
    DayOfWeek.THURSDAY -> "Чт"
    DayOfWeek.FRIDAY -> "Пт"
    DayOfWeek.SATURDAY -> "Сб"
    DayOfWeek.SUNDAY -> "Вс"
}

@Preview
@Composable
private fun DailyForecastCardPreview() {
    AppTheme {
        DailyForecastCard(daily = WeatherDetailPreviewData.detail.daily)
    }
}
