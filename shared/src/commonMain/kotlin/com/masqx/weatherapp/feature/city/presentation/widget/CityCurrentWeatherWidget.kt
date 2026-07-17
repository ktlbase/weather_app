package com.masqx.weatherapp.feature.city.presentation.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.masqx.weatherapp.core.theme.AppThemeTokens
import com.masqx.weatherapp.core.ui.AnimatedCounter
import com.masqx.weatherapp.feature.weather.domain.CityCurrentWeather
import com.masqx.weatherapp.feature.weather.presentation.widget.formatLocalTime
import com.masqx.weatherapp.feature.weather.presentation.widget.rememberCurrentLocalTime
import com.masqx.weatherapp.feature.weather.presentation.widget.weatherDescriptionFor
import com.masqx.weatherapp.feature.weather.presentation.widget.weatherIconFor
import com.masqx.weatherapp.feature.weather.presentation.widget.weatherStripeColorsFor

@Composable
fun CityCurrentWeatherCard(
    cityWeather: CityCurrentWeather,
    onClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier,
) {
    val city = cityWeather.city
    val weather = cityWeather.currentWeather
    val localTime = rememberCurrentLocalTime(city.timezone)
    val shape = AppThemeTokens.shapes.lg

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .shadow(elevation = 2.dp, shape = shape)
            .clip(shape)
            .background(MaterialTheme.colorScheme.surface)
            .then(if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        val stripeColors = weather?.weatherCode?.let { weatherStripeColorsFor(it) }
            ?: List(4) { MaterialTheme.colorScheme.primary }

        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(60.dp),
            contentAlignment = Alignment.Center,
        ) {
            Column(modifier = Modifier.matchParentSize()) {
                stripeColors.forEach { stripeColor ->
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .background(stripeColor),
                    )
                }
            }

            if (weather?.weatherCode != null) {
                Icon(
                    imageVector = weatherIconFor(weather.weatherCode),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(28.dp),
                )
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = AppThemeTokens.spacing.md,
                    vertical = AppThemeTokens.spacing.md,
                ),
        ) {
            Column(modifier = Modifier.weight(1f, fill = false)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = city.name,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f, fill = false),
                    )
                    Spacer(Modifier.width(6.dp))
                    Text(
                        text = "· ${formatLocalTime(localTime)}",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = .6f),
                        maxLines = 1,
                    )
                }
                Text(
                    text = weather?.weatherCode?.let { weatherDescriptionFor(it) }
                        ?: city.region?.let { "$it, ${city.country}" }
                        ?: city.country,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = .7f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }

            Spacer(Modifier.width(AppThemeTokens.spacing.sm))

            when {
                weather?.temperature != null -> {
                    AnimatedCounter(targetValue = weather.temperature.celsius) { animatedCelsius ->
                        Text(
                            text = "$animatedCelsius°",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onSurface,
                        )
                    }
                }

                else -> CircularProgressIndicator(
                    modifier = Modifier.size(16.dp),
                    strokeWidth = 2.dp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                )
            }
        }
    }
}

@Composable
@Preview
private fun CityCurrentWeatherCardPreview() {
    CityCurrentWeatherCard(cityWeather = CityPreviewData.defaultCityWeather)
}

@Composable
@Preview
private fun CityCurrentWeatherCardLongNamePreview() {
    val longNameCity = CityPreviewData.defaultCityWeather.city.copy(
        name = "Санкт-Петербург-на-Неве-Историческая-Столица",
        region = "Северо-Западный федеральный округ",
    )
    CityCurrentWeatherCard(
        cityWeather = CityPreviewData.defaultCityWeather.copy(city = longNameCity),
    )
}
