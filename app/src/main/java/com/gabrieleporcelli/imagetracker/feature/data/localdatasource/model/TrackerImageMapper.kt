package com.gabrieleporcelli.imagetracker.feature.data.localdatasource.model

import com.gabrieleporcelli.imagetracker.core.domain.model.TrackedImage
import com.google.android.gms.maps.model.LatLng

fun TrackedImageEntity.toDomain(): TrackedImage =
    TrackedImage(
        location = location
            .split(",")
            .let { LatLng(it[0].toDouble(), it[1].toDouble()) },
        url = url
    )


fun TrackedImage.toEntity(): TrackedImageEntity =
    TrackedImageEntity(
        id = id,
        location = "${location.latitude},${location.longitude}",
        url = url
    )