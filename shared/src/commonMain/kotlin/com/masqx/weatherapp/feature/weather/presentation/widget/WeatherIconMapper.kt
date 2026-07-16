package com.masqx.weatherapp.feature.weather.presentation.widget

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import compose.icons.WeatherIcons
import compose.icons.weathericons.Cloud
import compose.icons.weathericons.DayCloudy
import compose.icons.weathericons.DayFog
import compose.icons.weathericons.DayHail
import compose.icons.weathericons.DayRain
import compose.icons.weathericons.DaySleet
import compose.icons.weathericons.DaySnow
import compose.icons.weathericons.DaySprinkle
import compose.icons.weathericons.DaySunny
import compose.icons.weathericons.DaySunnyOvercast
import compose.icons.weathericons.DayThunderstorm
import compose.icons.weathericons.NightAltCloudy
import compose.icons.weathericons.NightAltHail
import compose.icons.weathericons.NightAltRain
import compose.icons.weathericons.NightAltSleet
import compose.icons.weathericons.NightAltSnow
import compose.icons.weathericons.NightAltSprinkle
import compose.icons.weathericons.NightAltThunderstorm
import compose.icons.weathericons.NightClear
import compose.icons.weathericons.NightFog
import com.masqx.weatherapp.feature.weather.domain.WeatherCode

/**
 * Иконка для WMO-кода погоды (см. [WeatherCode]) с учётом дня/ночи.
 * Источник иконок: Weather Icons by Erik Flowers.
 */
@Composable
fun weatherIconFor(code: WeatherCode, isDay: Boolean = true): ImageVector = when (code) {
    WeatherCode.CLEAR_SKY -> if (isDay) WeatherIcons.DaySunny else WeatherIcons.NightClear
    WeatherCode.MAINLY_CLEAR -> if (isDay) WeatherIcons.DaySunnyOvercast else WeatherIcons.NightAltCloudy
    WeatherCode.PARTLY_CLOUDY -> if (isDay) WeatherIcons.DayCloudy else WeatherIcons.NightAltCloudy
    WeatherCode.OVERCAST -> WeatherIcons.Cloud

    WeatherCode.FOG,
    WeatherCode.DEPOSITING_RIME_FOG,
        -> if (isDay) WeatherIcons.DayFog else WeatherIcons.NightFog

    WeatherCode.DRIZZLE_LIGHT,
    WeatherCode.DRIZZLE_MODERATE,
    WeatherCode.DRIZZLE_DENSE,
        -> if (isDay) WeatherIcons.DaySprinkle else WeatherIcons.NightAltSprinkle

    WeatherCode.FREEZING_DRIZZLE_LIGHT,
    WeatherCode.FREEZING_DRIZZLE_DENSE,
        -> if (isDay) WeatherIcons.DaySleet else WeatherIcons.NightAltSleet

    WeatherCode.RAIN_SLIGHT,
    WeatherCode.RAIN_MODERATE,
    WeatherCode.RAIN_HEAVY,
        -> if (isDay) WeatherIcons.DayRain else WeatherIcons.NightAltRain

    WeatherCode.FREEZING_RAIN_LIGHT,
    WeatherCode.FREEZING_RAIN_HEAVY,
        -> if (isDay) WeatherIcons.DaySleet else WeatherIcons.NightAltSleet

    WeatherCode.SNOW_FALL_SLIGHT,
    WeatherCode.SNOW_FALL_MODERATE,
    WeatherCode.SNOW_FALL_HEAVY,
    WeatherCode.SNOW_GRAINS,
        -> if (isDay) WeatherIcons.DaySnow else WeatherIcons.NightAltSnow

    WeatherCode.RAIN_SHOWERS_SLIGHT,
    WeatherCode.RAIN_SHOWERS_MODERATE,
    WeatherCode.RAIN_SHOWERS_VIOLENT,
        -> if (isDay) WeatherIcons.DayRain else WeatherIcons.NightAltRain

    WeatherCode.SNOW_SHOWERS_SLIGHT,
    WeatherCode.SNOW_SHOWERS_HEAVY,
        -> if (isDay) WeatherIcons.DaySnow else WeatherIcons.NightAltSnow

    WeatherCode.THUNDERSTORM_SLIGHT_OR_MODERATE ->
        if (isDay) WeatherIcons.DayThunderstorm else WeatherIcons.NightAltThunderstorm

    WeatherCode.THUNDERSTORM_WITH_SLIGHT_HAIL,
    WeatherCode.THUNDERSTORM_WITH_HEAVY_HAIL,
        -> if (isDay) WeatherIcons.DayHail else WeatherIcons.NightAltHail
}

/**
 * Цветовая категория погоды (design.md: Primary — стандартные условия, Secondary — осадки/влага,
 * Tertiary — суровые/опасные явления). Не зависит от темы — фиксированные акценты для мгновенного
 * визуального различения статуса в списке городов.
 */
@Composable
fun weatherColorFor(code: WeatherCode): Color = when (code) {
    WeatherCode.CLEAR_SKY,
    WeatherCode.MAINLY_CLEAR,
        -> Color(0xFFF5A623) // ясно — солнечный янтарь

    WeatherCode.PARTLY_CLOUDY,
    WeatherCode.OVERCAST,
    WeatherCode.FOG,
    WeatherCode.DEPOSITING_RIME_FOG,
        -> Color(0xFF767586) // облачно/туман — нейтральный slate (design.md outline)

    WeatherCode.DRIZZLE_LIGHT,
    WeatherCode.DRIZZLE_MODERATE,
    WeatherCode.DRIZZLE_DENSE,
    WeatherCode.RAIN_SLIGHT,
    WeatherCode.RAIN_MODERATE,
    WeatherCode.RAIN_HEAVY,
    WeatherCode.RAIN_SHOWERS_SLIGHT,
    WeatherCode.RAIN_SHOWERS_MODERATE,
    WeatherCode.RAIN_SHOWERS_VIOLENT,
        -> Color(0xFF006591) // дождь/влага — design.md secondary (sky blue)

    WeatherCode.FREEZING_DRIZZLE_LIGHT,
    WeatherCode.FREEZING_DRIZZLE_DENSE,
    WeatherCode.FREEZING_RAIN_LIGHT,
    WeatherCode.FREEZING_RAIN_HEAVY,
    WeatherCode.SNOW_FALL_SLIGHT,
    WeatherCode.SNOW_FALL_MODERATE,
    WeatherCode.SNOW_FALL_HEAVY,
    WeatherCode.SNOW_GRAINS,
    WeatherCode.SNOW_SHOWERS_SLIGHT,
    WeatherCode.SNOW_SHOWERS_HEAVY,
        -> Color(0xFF39B8FD) // снег/наледь — светлый ледяной голубой

    WeatherCode.THUNDERSTORM_SLIGHT_OR_MODERATE,
    WeatherCode.THUNDERSTORM_WITH_SLIGHT_HAIL,
    WeatherCode.THUNDERSTORM_WITH_HEAVY_HAIL,
        -> Color(0xFFB90538) // гроза/град — design.md tertiary (rose), опасные явления
}

/** Верх/низ диапазона тона для плашки статуса погоды — крайние точки, между которыми считаются полосы-шторки. */
@Composable
private fun weatherGradientRangeFor(code: WeatherCode): Pair<Color, Color> = when (code) {
    WeatherCode.CLEAR_SKY,
    WeatherCode.MAINLY_CLEAR,
        -> Color(0xFFFFCB6B) to Color(0xFFF5A623)

    WeatherCode.PARTLY_CLOUDY,
    WeatherCode.OVERCAST,
    WeatherCode.FOG,
    WeatherCode.DEPOSITING_RIME_FOG,
        -> Color(0xFFAEB4C2) to Color(0xFF6B7280)

    WeatherCode.DRIZZLE_LIGHT,
    WeatherCode.DRIZZLE_MODERATE,
    WeatherCode.DRIZZLE_DENSE,
    WeatherCode.RAIN_SLIGHT,
    WeatherCode.RAIN_MODERATE,
    WeatherCode.RAIN_HEAVY,
    WeatherCode.RAIN_SHOWERS_SLIGHT,
    WeatherCode.RAIN_SHOWERS_MODERATE,
    WeatherCode.RAIN_SHOWERS_VIOLENT,
        -> Color(0xFF7FB2E5) to Color(0xFF3D6FB4)

    WeatherCode.FREEZING_DRIZZLE_LIGHT,
    WeatherCode.FREEZING_DRIZZLE_DENSE,
    WeatherCode.FREEZING_RAIN_LIGHT,
    WeatherCode.FREEZING_RAIN_HEAVY,
    WeatherCode.SNOW_FALL_SLIGHT,
    WeatherCode.SNOW_FALL_MODERATE,
    WeatherCode.SNOW_FALL_HEAVY,
    WeatherCode.SNOW_GRAINS,
    WeatherCode.SNOW_SHOWERS_SLIGHT,
    WeatherCode.SNOW_SHOWERS_HEAVY,
        -> Color(0xFFBEE3FB) to Color(0xFF39B8FD)

    WeatherCode.THUNDERSTORM_SLIGHT_OR_MODERATE,
    WeatherCode.THUNDERSTORM_WITH_SLIGHT_HAIL,
    WeatherCode.THUNDERSTORM_WITH_HEAVY_HAIL,
        -> Color(0xFFDC2C4F) to Color(0xFFB90538)
}

/**
 * 4 сплошных оттенка "шторками" сверху вниз для плашки статуса погоды — не гладкий градиент,
 * а квантованные полосы, посчитанные линейной интерполяцией между крайними тонами категории.
 */
@Composable
fun weatherStripeColorsFor(code: WeatherCode): List<Color> {
    val (top, bottom) = weatherGradientRangeFor(code)
    val stripeCount = 4
    return List(stripeCount) { index ->
        val fraction = index / (stripeCount - 1).toFloat()
        lerp(top, bottom, fraction)
    }
}

private fun lerp(start: Color, stop: Color, fraction: Float): Color = Color(
    red = start.red + (stop.red - start.red) * fraction,
    green = start.green + (stop.green - start.green) * fraction,
    blue = start.blue + (stop.blue - start.blue) * fraction,
    alpha = start.alpha + (stop.alpha - start.alpha) * fraction,
)

/** Краткое описание погоды на русском для карточки города (design.md: "Clear sky", "Overcast", "Light rain"). */
fun weatherDescriptionFor(code: WeatherCode): String = when (code) {
    WeatherCode.CLEAR_SKY -> "Ясно"
    WeatherCode.MAINLY_CLEAR -> "Малооблачно"
    WeatherCode.PARTLY_CLOUDY -> "Переменная облачность"
    WeatherCode.OVERCAST -> "Облачно"
    WeatherCode.FOG, WeatherCode.DEPOSITING_RIME_FOG -> "Туман"
    WeatherCode.DRIZZLE_LIGHT -> "Небольшая морось"
    WeatherCode.DRIZZLE_MODERATE -> "Морось"
    WeatherCode.DRIZZLE_DENSE -> "Сильная морось"
    WeatherCode.FREEZING_DRIZZLE_LIGHT, WeatherCode.FREEZING_DRIZZLE_DENSE -> "Ледяная морось"
    WeatherCode.RAIN_SLIGHT -> "Небольшой дождь"
    WeatherCode.RAIN_MODERATE -> "Дождь"
    WeatherCode.RAIN_HEAVY -> "Сильный дождь"
    WeatherCode.FREEZING_RAIN_LIGHT, WeatherCode.FREEZING_RAIN_HEAVY -> "Ледяной дождь"
    WeatherCode.SNOW_FALL_SLIGHT -> "Небольшой снег"
    WeatherCode.SNOW_FALL_MODERATE -> "Снег"
    WeatherCode.SNOW_FALL_HEAVY -> "Сильный снег"
    WeatherCode.SNOW_GRAINS -> "Снежная крупа"
    WeatherCode.RAIN_SHOWERS_SLIGHT -> "Небольшой ливень"
    WeatherCode.RAIN_SHOWERS_MODERATE -> "Ливень"
    WeatherCode.RAIN_SHOWERS_VIOLENT -> "Сильный ливень"
    WeatherCode.SNOW_SHOWERS_SLIGHT -> "Небольшой снегопад"
    WeatherCode.SNOW_SHOWERS_HEAVY -> "Сильный снегопад"
    WeatherCode.THUNDERSTORM_SLIGHT_OR_MODERATE -> "Гроза"
    WeatherCode.THUNDERSTORM_WITH_SLIGHT_HAIL, WeatherCode.THUNDERSTORM_WITH_HEAVY_HAIL -> "Гроза с градом"
}
