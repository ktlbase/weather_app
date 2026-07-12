package com.masqx.weatherapp.core.service.location

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import kotlinx.coroutines.suspendCancellableCoroutine
import platform.CoreLocation.CLAuthorizationStatus
import platform.CoreLocation.CLLocation
import platform.CoreLocation.CLLocationManager
import platform.CoreLocation.CLLocationManagerDelegateProtocol
import platform.CoreLocation.kCLAuthorizationStatusAuthorizedAlways
import platform.CoreLocation.kCLAuthorizationStatusAuthorizedWhenInUse
import platform.CoreLocation.kCLLocationAccuracyHundredMeters
import platform.Foundation.NSError
import platform.darwin.NSObject
import kotlin.coroutines.resume

actual class LocationService {
    actual fun hasLocationPermission(): Boolean {
        val status: CLAuthorizationStatus = CLLocationManager().authorizationStatus
        return status == kCLAuthorizationStatusAuthorizedAlways ||
            status == kCLAuthorizationStatusAuthorizedWhenInUse
    }

    @OptIn(ExperimentalForeignApi::class)
    actual suspend fun getCurrentLocation(): Coordinates? =
        suspendCancellableCoroutine { cont ->
            val manager = CLLocationManager()
            manager.desiredAccuracy = kCLLocationAccuracyHundredMeters

            val delegate = object : NSObject(), CLLocationManagerDelegateProtocol {
                override fun locationManager(manager: CLLocationManager, didUpdateLocations: List<*>) {
                    manager.stopUpdatingLocation()
                    val location = didUpdateLocations.lastOrNull() as? CLLocation
                    if (cont.isActive) {
                        cont.resume(
                            location?.coordinate?.useContents {
                                Coordinates(latitude = latitude, longitude = longitude)
                            },
                        )
                    }
                }

                override fun locationManager(manager: CLLocationManager, didFailWithError: NSError) {
                    manager.stopUpdatingLocation()
                    if (cont.isActive) cont.resume(null)
                }
            }

            manager.delegate = delegate
            manager.requestWhenInUseAuthorization()
            manager.requestLocation()

            cont.invokeOnCancellation { manager.stopUpdatingLocation() }
        }
}
