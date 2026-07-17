package com.masqx.weatherapp.feature.city.presentation.widget

import com.masqx.weatherapp.feature.city.domain.entity.City
import com.masqx.weatherapp.feature.city.domain.entity.CityId
import com.masqx.weatherapp.feature.city.domain.entity.Location
import com.masqx.weatherapp.feature.city.domain.entity.Timezone
import com.masqx.weatherapp.feature.weather.domain.CityCurrentWeather
import com.masqx.weatherapp.feature.weather.domain.CurrentWeather
import com.masqx.weatherapp.feature.weather.domain.Degree
import com.masqx.weatherapp.feature.weather.domain.WeatherCode

/**
 * Мок-данные только для @Preview. Не референсь из prod-кода — держи вызовы этого
 * объекта внутри private-функций, помеченных @Preview, чтобы не тянуть моки в реальный граф UI.
 */
internal object CityPreviewData {
    val defaultCity = City(
        id = CityId("1"),
        name = "Moscow",
        location = Location(latitude = 55.7558, longitude = 37.6173),
        country = "Russia",
        timezone = Timezone("Europe/Moscow"),
    )

    val defaultCurrentWeather = CurrentWeather(
        temperature = Degree(21),
        relativeHumidityPercent = 54,
        apparentTemperature = Degree(20),
        isDay = true,
        precipitationMm = 0.0,
        rainMm = 0.0,
        showersMm = 0.0,
        snowfallCm = 0.0,
        weatherCode = WeatherCode.PARTLY_CLOUDY,
        cloudCoverPercent = 40,
        pressureMslHpa = 1013.0,
        surfacePressureHpa = 1009.0,
        windSpeedKmh = 12.5,
        windDirectionDegrees = 220,
        windGustsKmh = 18.0,
    )

    val defaultCityWeather = CityCurrentWeather(
        city = defaultCity,
        currentWeather = defaultCurrentWeather,
    )
}
