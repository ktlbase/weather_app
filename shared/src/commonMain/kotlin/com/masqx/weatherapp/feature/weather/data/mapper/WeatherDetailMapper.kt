package com.masqx.weatherapp.feature.weather.data.mapper

import com.masqx.weatherapp.feature.city.domain.entity.Timezone
import com.masqx.weatherapp.feature.weather.data.dto.WeatherDetailDto
import com.masqx.weatherapp.feature.weather.domain.CityWeatherDetail
import com.masqx.weatherapp.feature.weather.domain.DailyForecast
import com.masqx.weatherapp.feature.weather.domain.Degree
import com.masqx.weatherapp.feature.weather.domain.HourlyForecast
import com.masqx.weatherapp.feature.weather.domain.WeatherCode
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toLocalDateTime
import kotlin.math.roundToInt
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

private const val HOURLY_FORECAST_HOURS = 24

/**
 * Собирает [CityWeatherDetail] из ответа Open-Meteo. Hourly-часть обрезается: старт — текущий
 * час города (время в ответе уже локальное, запрос идёт с timezone=auto), дальше 24 часа.
 * Метрики плиток (точка росы, видимость) берутся из hourly по индексу текущего часа.
 */
@OptIn(ExperimentalTime::class)
fun WeatherDetailDto.toDomain(timezone: Timezone): CityWeatherDetail {
    val now = Clock.System.now().toLocalDateTime(timezone.toKotlinTimeZone())

    val hourlyTimes = hourly.time.map { LocalDateTime.parse(it) }
    val nowIndex = hourlyTimes.indexOfFirst {
        it.date == now.date && it.hour == now.hour
    }.coerceAtLeast(0)

    val hourlyForecast = (nowIndex until hourlyTimes.size)
        .take(HOURLY_FORECAST_HOURS)
        .mapNotNull { index ->
            val code = WeatherCode.entries.firstOrNull { it.code == hourly.weatherCode[index] }
                ?: return@mapNotNull null
            HourlyForecast(
                time = hourlyTimes[index],
                temperature = Degree(hourly.temperature2m[index].roundToInt()),
                weatherCode = code,
                isDay = hourly.isDay[index] != 0,
            )
        }

    val dailyForecast = daily.time.indices.mapNotNull { index ->
        val code = WeatherCode.entries.firstOrNull { it.code == daily.weatherCode[index] }
            ?: return@mapNotNull null
        DailyForecast(
            date = LocalDate.parse(daily.time[index]),
            weatherCode = code,
            temperatureMin = Degree(daily.temperatureMin[index].roundToInt()),
            temperatureMax = Degree(daily.temperatureMax[index].roundToInt()),
        )
    }

    return CityWeatherDetail(
        current = current.toDomain(),
        hourly = hourlyForecast,
        daily = dailyForecast,
        dewPoint = hourly.dewPoint2m.getOrNull(nowIndex)?.let { Degree(it.roundToInt()) },
        uvIndexMax = daily.uvIndexMax.firstOrNull(),
        visibilityKm = hourly.visibility.getOrNull(nowIndex)?.let { it / 1000.0 },
    )
}
