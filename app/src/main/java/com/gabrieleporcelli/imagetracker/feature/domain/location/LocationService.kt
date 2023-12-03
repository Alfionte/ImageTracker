package com.gabrieleporcelli.imagetracker.feature.domain.location

import android.app.Service
import android.content.Intent
import android.location.Location
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.gabrieleporcelli.imagetracker.R
import com.gabrieleporcelli.imagetracker.application.TrackerApp.Companion.CHANNEL_ID
import com.gabrieleporcelli.imagetracker.core.domain.model.TrackedImage
import com.gabrieleporcelli.imagetracker.feature.domain.usecases.SaveTrackedImageUseCase
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.SphericalUtil.computeDistanceBetween
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@AndroidEntryPoint
class LocationService : Service() {

    @Inject
    lateinit var locationClient: LocationClient
    @Inject
    lateinit var saveTrackedImageUseCase: SaveTrackedImageUseCase

    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    override fun onBind(p0: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_START -> start()
            ACTION_STOP -> stop()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun start() {
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(getString(R.string.notification_title))
            .setContentText(getString(R.string.notification_text))
            .setSmallIcon(R.drawable.ic_launcher)
            .setOngoing(true)

        var lastLocation: Location? = null

        locationClient.getLocationUpdates(LOCATION_INTERVAL)
            .catch { e -> e.printStackTrace() }
            .onEach { location ->
                if (lastLocation == null) {
                    lastLocation = location
                }

                val lastLatLng = LatLng(lastLocation!!.latitude, lastLocation!!.longitude)
                val newLatLng = LatLng(location.latitude, location.longitude)

                if (computeDistanceBetween(lastLatLng, newLatLng) > LOCATION_DISTANCE_METERS) {
                    lastLocation = location
                    saveTrackedImageUseCase.saveTrackedImage(
                        TrackedImage(
                            location = LatLng(
                                location.latitude,
                                location.longitude
                            )
                        )
                    )
                }
            }
            .launchIn(serviceScope)

        startForeground(LOCATION_SERVICE_ID, notification.build())
    }

    private fun stop() {
        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()
    }

    override fun onDestroy() {
        serviceScope.cancel()
        super.onDestroy()
    }

    companion object {
        const val LOCATION_SERVICE_ID = 15858
        const val ACTION_START =
            "com.gabrieleporcelli.imagetracker.feature.domain.location.LocationService.ACTION_START"
        const val ACTION_STOP =
            "com.gabrieleporcelli.imagetracker.feature.domain.location.LocationService.ACTION_STOP"

        const val LOCATION_INTERVAL = 10000L
        const val LOCATION_DISTANCE_METERS = 100
    }
}