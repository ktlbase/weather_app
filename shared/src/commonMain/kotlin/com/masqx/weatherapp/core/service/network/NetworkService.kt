package com.masqx.weatherapp.core.service.network

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

abstract class NetworkService(
    val baseUrl: String,
    extraPlugins: HttpClientConfig<*>.() -> Unit = {},
) {
    private val client = HttpClient {
        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                }
            )
        }
        defaultRequest {
            contentType(ContentType.Application.Json)
        }
        extraPlugins()
    }

    suspend fun get(
        path: String,
        params: Map<String, String> = emptyMap(),
    ): HttpResponse = client.get("$baseUrl$path") {
        params.forEach { (key, value) -> parameter(key, value) }
    }

    suspend fun post(
        path: String,
        body: Any? = null,
        params: Map<String, String> = emptyMap(),
    ): HttpResponse = client.post("$baseUrl$path") {
        params.forEach { (key, value) -> parameter(key, value) }
        if (body != null) setBody(body)
    }

    suspend fun put(
        path: String,
        body: Any? = null,
        params: Map<String, String> = emptyMap(),
    ): HttpResponse = client.put("$baseUrl$path") {
        params.forEach { (key, value) -> parameter(key, value) }
        if (body != null) setBody(body)
    }

    suspend fun delete(
        path: String,
        params: Map<String, String> = emptyMap(),
    ): HttpResponse = client.delete("$baseUrl$path") {
        params.forEach { (key, value) -> parameter(key, value) }
    }
}
