package com.masqx.weatherapp.core.service.location

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import kotlinx.coroutines.suspendCancellableCoroutine
import platform.CoreLocation.CLAuthorizationStatus
import platform.CoreLocation.CLGeocoder
import platform.CoreLocation.CLLocation
import platform.CoreLocation.CLLocationManager
import platform.CoreLocation.CLLocationManagerDelegateProtocol
import platform.CoreLocation.CLPlacemark
import platform.CoreLocation.kCLAuthorizationStatusAuthorizedAlways
import platform.CoreLocation.kCLAuthorizationStatusAuthorizedWhenInUse
import platform.CoreLocation.kCLAuthorizationStatusNotDetermined
import platform.CoreLocation.kCLLocationAccuracyHundredMeters
import platform.Foundation.NSError
import platform.darwin.NSObject
import kotlin.coroutines.resume

actual class LocationService {
    // Держим manager и delegate как поля: CLLocationManager.delegate — weak-ссылка,
    // локальный delegate внутри suspend-функции мог бы быть собран до колбэка.
    private val manager = CLLocationManager()
    private var delegate: LocationDelegate? = null

    actual fun hasLocationPermission(): Boolean =
        isAuthorized(CLLocationManager().authorizationStatus)

    private fun isAuthorized(status: CLAuthorizationStatus): Boolean =
        status == kCLAuthorizationStatusAuthorizedAlways ||
            status == kCLAuthorizationStatusAuthorizedWhenInUse

    actual suspend fun getCurrentLocation(): Coordinates? =
        suspendCancellableCoroutine { cont ->
            manager.desiredAccuracy = kCLLocationAccuracyHundredMeters

            val newDelegate = LocationDelegate(
                onResult = { coordinates ->
                    delegate = null
                    if (cont.isActive) cont.resume(coordinates)
                },
                isAuthorized = ::isAuthorized,
                requestLocation = { manager.requestLocation() },
            )
            delegate = newDelegate
            manager.delegate = newDelegate

            // Система сама зовёт locationManagerDidChangeAuthorization при установке delegate —
            // delegate и решает, звать ли requestLocation (или вернуть null при отказе).
            // Здесь только показываем системный промпт, если статус ещё не определён:
            // requestLocation до ответа юзера упал бы с ошибкой.
            if (manager.authorizationStatus == kCLAuthorizationStatusNotDetermined) {
                manager.requestWhenInUseAuthorization()
            }

            cont.invokeOnCancellation {
                delegate = null
                manager.stopUpdatingLocation()
            }
        }

    @OptIn(ExperimentalForeignApi::class)
    actual suspend fun getCityName(coordinates: Coordinates): String? =
        suspendCancellableCoroutine { cont ->
            val location = CLLocation(
                latitude = coordinates.latitude,
                longitude = coordinates.longitude,
            )
            CLGeocoder().reverseGeocodeLocation(location) { placemarks, _ ->
                val placemark = placemarks?.firstOrNull() as? CLPlacemark
                if (cont.isActive) {
                    cont.resume(
                        placemark?.locality
                            ?: placemark?.subAdministrativeArea
                            ?: placemark?.administrativeArea,
                    )
                }
            }
        }
}

@OptIn(ExperimentalForeignApi::class)
private class LocationDelegate(
    private val onResult: (Coordinates?) -> Unit,
    private val isAuthorized: (CLAuthorizationStatus) -> Boolean,
    private val requestLocation: () -> Unit,
) : NSObject(), CLLocationManagerDelegateProtocol {
    private var locationRequested = false

    override fun locationManagerDidChangeAuthorization(manager: CLLocationManager) {
        val status = manager.authorizationStatus
        when {
            status == kCLAuthorizationStatusNotDetermined -> Unit // промпт ещё на экране
            isAuthorized(status) -> {
                if (!locationRequested) {
                    locationRequested = true
                    requestLocation()
                }
            }
            else -> onResult(null)
        }
    }

    override fun locationManager(manager: CLLocationManager, didUpdateLocations: List<*>) {
        manager.stopUpdatingLocation()
        val location = didUpdateLocations.lastOrNull() as? CLLocation
        onResult(
            location?.coordinate?.useContents {
                Coordinates(latitude = latitude, longitude = longitude)
            },
        )
    }

    override fun locationManager(manager: CLLocationManager, didFailWithError: NSError) {
        manager.stopUpdatingLocation()
        onResult(null)
    }
}
