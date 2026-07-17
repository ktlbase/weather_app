package com.masqx.weatherapp.core.service.location

expect class LocationService {
    fun hasLocationPermission(): Boolean
    suspend fun getCurrentLocation(): Coordinates?

    /** Обратный геокодинг: название города по координатам (платформенный геокодер). */
    suspend fun getCityName(coordinates: Coordinates): String?
}
