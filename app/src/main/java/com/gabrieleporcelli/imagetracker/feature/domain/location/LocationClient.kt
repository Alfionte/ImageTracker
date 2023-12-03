package com.gabrieleporcelli.imagetracker.feature.domain.location

import android.location.Location
import kotlinx.coroutines.flow.Flow

interface LocationClient {

    fun getLocationUpdates(interval: Long): Flow<Location>
}