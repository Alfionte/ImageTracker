package com.gabrieleporcelli.imagetracker.feature.data.localdatasource

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gabrieleporcelli.imagetracker.feature.data.localdatasource.model.TrackedImageEntity

@Database(entities = [TrackedImageEntity::class], version = 1)
abstract class TrackerImageDatabase : RoomDatabase() {
    abstract fun charactersDao(): TrackerImageDao
}