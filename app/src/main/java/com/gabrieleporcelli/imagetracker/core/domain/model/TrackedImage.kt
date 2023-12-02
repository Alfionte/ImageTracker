package com.gabrieleporcelli.imagetracker.core.domain.model

import com.google.android.gms.maps.model.LatLng

data class TrackedImage(
    val location: LatLng,
    val url: String? = null,
) {
    val id: String = location.hashCode().toString()
    val isValid: Boolean
        get() = url != null
}