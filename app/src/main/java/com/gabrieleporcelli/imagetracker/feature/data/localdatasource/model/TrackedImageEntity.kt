package com.gabrieleporcelli.imagetracker.feature.data.localdatasource.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TrackedImageEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "location") val location: String,
    @ColumnInfo(name = "url") val url: String? = null,
)