package com.masqx.weatherapp.core.service.location

expect class LocationService {
    fun hasLocationPermission(): Boolean
    suspend fun getCurrentLocation(): Coordinates?
}
