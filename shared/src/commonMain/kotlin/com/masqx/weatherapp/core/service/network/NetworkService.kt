package com.masqx.weatherapp.core.service.network

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

abstract class NetworkService(
    val baseUrl: String,
    extraPlugins: HttpClientConfig<*>.() -> Unit = {},
) {
    val client = HttpClient {
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

    suspend inline fun <reified T> get(
        path: String,
        params: Map<String, String> = emptyMap(),
    ): T = client.get("$baseUrl$path") {
        params.forEach { (key, value) -> parameter(key, value) }
    }.body()

    suspend inline fun <reified T> post(
        path: String,
        body: Any? = null,
        params: Map<String, String> = emptyMap(),
    ): T = client.post("$baseUrl$path") {
        params.forEach { (key, value) -> parameter(key, value) }
        if (body != null) setBody(body)
    }.body()

    suspend inline fun <reified T> put(
        path: String,
        body: Any? = null,
        params: Map<String, String> = emptyMap(),
    ): T = client.put("$baseUrl$path") {
        params.forEach { (key, value) -> parameter(key, value) }
        if (body != null) setBody(body)
    }.body()

    suspend inline fun <reified T> delete(
        path: String,
        params: Map<String, String> = emptyMap(),
    ): T = client.delete("$baseUrl$path") {
        params.forEach { (key, value) -> parameter(key, value) }
    }.body()
}
