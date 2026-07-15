package com.masqx.weatherapp.core.service.network

// Клиент для геокодирования по городам
class GeocodingNetworkService : NetworkService(
    baseUrl = "https://geocoding-api.open-meteo.com/v1",
)