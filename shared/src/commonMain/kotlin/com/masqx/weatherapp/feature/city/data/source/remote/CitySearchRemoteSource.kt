package com.masqx.weatherapp.feature.city.data.source.remote

import com.masqx.weatherapp.core.service.network.GeocodingNetworkService
import com.masqx.weatherapp.feature.city.data.dto.CityDto
import io.ktor.client.call.body
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonArray

interface CitySearchRemoteSource {
    suspend fun search(query: String): List<CityDto>
}


class CitySearchRemoteSourceImpl(
    private val client: GeocodingNetworkService,
) : CitySearchRemoteSource {
    // The Ktor plugin config doesn't apply to manual decoding, so ignoreUnknownKeys is needed here too
    private val json = Json { ignoreUnknownKeys = true }

    override suspend fun search(query: String): List<CityDto> {
        val response = client.get(
            "/search",
            params = mapOf(
                "name" to query,
                "count" to "10",
                "language" to "ru",
            ),
        )
        val data = response.body<JsonObject>()
        val cityArray = data["results"]?.jsonArray ?: return emptyList();

        return cityArray.mapNotNull { element ->
            try {
                json.decodeFromJsonElement<CityDto>(element)
            } catch (e: SerializationException) {
                null
            }
        }
    }
}
