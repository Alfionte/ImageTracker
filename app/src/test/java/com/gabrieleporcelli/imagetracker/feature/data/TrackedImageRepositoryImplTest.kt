package com.gabrieleporcelli.imagetracker.feature.data

import app.cash.turbine.test
import com.gabrieleporcelli.imagetracker.core.domain.model.TrackedImage
import com.gabrieleporcelli.imagetracker.feature.data.localdatasource.TrackerImageLocalDataSource
import com.gabrieleporcelli.imagetracker.feature.data.remotedatasource.TrackerImageRemoteDataSource
import com.gabrieleporcelli.imagetracker.feature.data.remotedatasource.model.FlickrPhotoDetail
import com.gabrieleporcelli.imagetracker.feature.data.remotedatasource.model.FlickrResponse
import com.gabrieleporcelli.imagetracker.feature.domain.repository.TrackedImageRepository
import com.google.android.gms.maps.model.LatLng
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class TrackedImageRepositoryTest {

    private val remoteDataSource = mockk<TrackerImageRemoteDataSource>()
    private val localDataSource = mockk<TrackerImageLocalDataSource>()

    private lateinit var repository: TrackedImageRepository

    @Before
    fun setUp() {
        repository = TrackedImageRepositoryImpl(
            remoteDataSource = remoteDataSource,
            localDataSource = localDataSource,
        )
    }

    @Test
    fun `when fetching a list of photo from remote data source, return the successful result`() =
        runTest {
            val position = LatLng(28.583333, -16.233333)
            val response = mockk<FlickrResponse>()
            coEvery { remoteDataSource.fetchFlickrPhotos(position) } returns Result.success(response)

            val pageResponse = repository.fetchTrackedImagesByLocation(position)
            assertEquals(pageResponse, Result.success(response))
        }

    @Test
    fun `when fetching a photo detail from remote data source, return the successful result`() =
        runTest {
            val id = "ID_1232"
            val response = mockk<FlickrPhotoDetail>()
            coEvery { remoteDataSource.fetchFlickrPhoto(id) } returns Result.success(response)

            val pageResponse = repository.fetchTrackedImage(id)
            assertEquals(pageResponse, Result.success(response))
        }

    @Test
    fun `when saving a photo on local data source, invoke local save`() = runTest {
        coEvery { localDataSource.saveTrackedImage(any()) } returns Unit
        val trackedImage = mockk<TrackedImage>()
        repository.saveTrackedImage(trackedImage)
        coEvery { localDataSource.saveTrackedImage(trackedImage) }
    }

    @Test
    fun `when updating a photo on local data source, invoke local update`() = runTest {
        coEvery { localDataSource.updateTrackedImage(any()) } returns Unit
        val trackedImage = mockk<TrackedImage>()
        repository.updateTrackedImage(trackedImage)
        coEvery { localDataSource.updateTrackedImage(trackedImage) }
    }

    @Test
    fun `when deleting all photos on local data source, invoke local delete`() = runTest {
        coEvery { localDataSource.deleteAllTrackedImage() } returns Unit
        repository.deleteAllTrackedImage()
        coEvery { localDataSource.deleteAllTrackedImage() }
    }

    @Test
    fun `when get photo stream from local source, retrieve them correctly`() = runTest {
        val firstItem = mockk<List<TrackedImage>>()
        val secondItem = mockk<List<TrackedImage>>()
        val trackedImageStream = flow {
            emit(firstItem)
            emit(secondItem)
        }
        coEvery { localDataSource.getTrackedImageStream() } returns trackedImageStream
        repository.getTrackedImageStream().test {
            assertEquals(firstItem, awaitItem())
            assertEquals(secondItem, awaitItem())
            awaitComplete()
        }
    }
}