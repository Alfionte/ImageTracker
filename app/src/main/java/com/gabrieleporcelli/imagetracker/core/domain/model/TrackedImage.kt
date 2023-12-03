package com.gabrieleporcelli.imagetracker.core.domain.model

import com.google.android.gms.maps.model.LatLng

data class TrackedImage(
    val location: LatLng,
    val url: String? = null,
) {
    val id: Int = location.hashCode()
}