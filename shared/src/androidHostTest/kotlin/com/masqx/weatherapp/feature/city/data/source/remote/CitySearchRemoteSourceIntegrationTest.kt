package com.masqx.weatherapp.feature.city.data.source.remote

import com.masqx.weatherapp.core.service.network.GeocodingNetworkService
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Tag
import kotlin.test.Test
import kotlin.test.assertTrue

// Integration test: hits the real Open-Meteo geocoding API, no mocks.
// Excluded from the default test task, run separately via `integrationTest`.
@Tag("integration")
class CitySearchRemoteSourceIntegrationTest {

    private val source: CitySearchRemoteSource = CitySearchRemoteSourceImpl(GeocodingNetworkService())

    @Test
    fun `search returns known city for a real query`() = runTest {
        val result = source.search("Moscow")

        assertTrue(result.isNotEmpty(), "expected at least one result for 'Moscow'")
        assertTrue(
            result.any { it.country == "Россия" },
            "expected a Russian city among results: $result",
        )
    }

    @Test
    fun `search returns empty list for nonsense query`() = runTest {
        val result = source.search("zzzxxqqnonexistentcity123")

        assertTrue(result.isEmpty())
    }
}
