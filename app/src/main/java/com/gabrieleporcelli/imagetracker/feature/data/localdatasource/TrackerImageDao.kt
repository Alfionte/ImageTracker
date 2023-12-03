package com.gabrieleporcelli.imagetracker.feature.data.localdatasource

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import androidx.room.Update
import com.gabrieleporcelli.imagetracker.feature.data.localdatasource.model.TrackedImageEntity
import kotlinx.coroutines.flow.Flow

@Suppress("unused")
@Dao
interface TrackerImageDao {
    @Query("SELECT * FROM trackedImageEntity")
    fun getAll(): Flow<List<TrackedImageEntity>>

    @Insert(onConflict = REPLACE)
    fun insert(vararg users: TrackedImageEntity)

    @Update
    fun update(character: TrackedImageEntity)

    @Query("DELETE FROM TrackedImageEntity")
    fun deleteAll()
}