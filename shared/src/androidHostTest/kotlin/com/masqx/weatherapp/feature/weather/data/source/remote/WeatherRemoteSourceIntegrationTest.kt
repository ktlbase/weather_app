package com.masqx.weatherapp.feature.weather.data.source.remote

import com.masqx.weatherapp.core.service.network.WeatherNetworkService
import com.masqx.weatherapp.feature.city.domain.entity.Location
import com.masqx.weatherapp.feature.weather.data.dto.CurrentWeatherParam
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Tag
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

// Integration test: hits the real Open-Meteo weather API, no mocks.
// Excluded from the default test task, run separately via `integrationTest`.
@Tag("integration")
class WeatherRemoteSourceIntegrationTest {

    private val source: WeatherRemoteSource = WeatherRemoteSourceImpl(WeatherNetworkService())

    private val moscow = Location(latitude = 55.7558, longitude = 37.6173)
    private val paris = Location(latitude = 48.8566, longitude = 2.3522)

    @Test
    fun `getCurrentWeatherForLocation returns weather for a single point`() = runTest {
        val result = source.getCurrentWeatherForLocation(moscow)

        assertNotNull(result.temperature2m)
        assertTrue(result.temperature2m!!.isFinite())
        assertNotNull(result.weatherCode)
    }

    @Test
    fun `getCurrentWeatherForLocationList returns weather per location in order`() = runTest {
        val locations = listOf(moscow, paris)

        val result = source.getCurrentWeatherForLocationList(locations)

        assertEquals(locations.size, result.size)
        result.forEach { assertNotNull(it.temperature2m) }
    }

    @Test
    fun `getCurrentWeatherForLocationList with one location matches single request shape`() = runTest {
        val single = source.getCurrentWeatherForLocation(moscow)
        val list = source.getCurrentWeatherForLocationList(listOf(moscow))

        assertEquals(1, list.size)
        assertEquals(single.weatherCode, list.first().weatherCode)
    }

    @Test
    fun `getCurrentWeatherForLocation only returns requested params`() = runTest {
        val result = source.getCurrentWeatherForLocation(
            moscow,
            params = setOf(CurrentWeatherParam.WIND_SPEED_10M, CurrentWeatherParam.WIND_GUSTS_10M),
        )

        assertNotNull(result.windSpeed10m)
        assertNotNull(result.windGusts10m)
        assertNull(result.temperature2m)
        assertNull(result.weatherCode)
    }
}
