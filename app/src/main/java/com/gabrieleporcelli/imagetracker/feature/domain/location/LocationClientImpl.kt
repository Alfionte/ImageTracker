package com.gabrieleporcelli.imagetracker.feature.domain.location

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationManager
import android.os.Looper
import com.gabrieleporcelli.imagetracker.core.extensions.hasFineLocationPermission
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class LocationClientImpl @Inject constructor(
    private val context: Context,
    private val client: FusedLocationProviderClient
) : LocationClient {

    private val locationManager = context.getSystemService(LocationManager::class.java)
    private val isGpsEnabled
        get() = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    private val isNetworkEnabled
        get() = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

    @SuppressLint("MissingPermission")
    override fun getLocationUpdates(interval: Long): Flow<Location> = callbackFlow {
        checkPermissions()
        val request = LocationRequest.Builder(interval).setIntervalMillis(interval).build()

        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                result.locations.lastOrNull()?.let { trySend(it) }
            }
        }
        client.requestLocationUpdates(
            request,
            locationCallback,
            Looper.getMainLooper()
        )
        awaitClose {
            client.removeLocationUpdates(locationCallback)
        }
    }

    private fun checkPermissions() {
        when {
            !context.hasFineLocationPermission() -> throw SecurityException("Location permission not granted")
            !isGpsEnabled && !isNetworkEnabled -> throw IllegalStateException("No location provider enabled")
        }
    }
}