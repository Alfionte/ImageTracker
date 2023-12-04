package com.gabrieleporcelli.imagetracker.feature.domain.usecases

import com.gabrieleporcelli.imagetracker.feature.data.remotedatasource.model.FlickrPhotoDetail
import com.gabrieleporcelli.imagetracker.feature.data.remotedatasource.model.FlickrPhotoDetailContent
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test


class UrlBuilderUseCaseTest {

    private lateinit var urlBuilderUseCase: UrlBuilderUseCase

    @Before
    fun setUp() {
        urlBuilderUseCase = UrlBuilderUseCaseImpl()
    }

    @Test
    fun `when building url from photo detail, return the correct url`() = runTest {
        val photoDetail = mockk<FlickrPhotoDetail>()
        val photoDetailContent = mockk<FlickrPhotoDetailContent>()
        every { photoDetailContent.id } returns "@#%$@#$2423"
        every { photoDetailContent.secret } returns "@#%$@#$"
        every { photoDetailContent.serverId} returns "6136874"
        every { photoDetailContent.farmId } returns 12
        every { photoDetail.photo } returns photoDetailContent
        val url = urlBuilderUseCase.buildUrl(photoDetail)
        assertEquals(url, "https://farm${photoDetail.photo.farmId}.staticflickr.com/${photoDetail.photo.serverId}/${photoDetail.photo.id}_${photoDetail.photo.secret}.jpg")
    }

    @Test
    fun `when building url from photo detail and small 240 request size, return the correct url`() = runTest {
        val photoDetail = mockk<FlickrPhotoDetail>()
        val photoDetailContent = mockk<FlickrPhotoDetailContent>()
        every { photoDetailContent.id } returns "@#%$@#$2423"
        every { photoDetailContent.secret } returns "@#%$@#$"
        every { photoDetailContent.serverId} returns "6136874"
        every { photoDetailContent.farmId } returns 12
        every { photoDetail.photo } returns photoDetailContent
        val url = urlBuilderUseCase.buildUrl(photoDetail, RequestSize.Small240)
        assertEquals(url, "https://farm${photoDetail.photo.farmId}.staticflickr.com/${photoDetail.photo.serverId}/${photoDetail.photo.id}_${photoDetail.photo.secret}_m.jpg")
    }

    @Test
    fun `when building url from photo detail and medium 640 request size, return the correct url`() = runTest {
        val photoDetail = mockk<FlickrPhotoDetail>()
        val photoDetailContent = mockk<FlickrPhotoDetailContent>()
        every { photoDetailContent.id } returns "@#%$@#$2423"
        every { photoDetailContent.secret } returns "@#%$@#$"
        every { photoDetailContent.serverId} returns "6136874"
        every { photoDetailContent.farmId } returns 12
        every { photoDetail.photo } returns photoDetailContent
        val url = urlBuilderUseCase.buildUrl(photoDetail, RequestSize.Medium640)
        assertEquals(url, "https://farm${photoDetail.photo.farmId}.staticflickr.com/${photoDetail.photo.serverId}/${photoDetail.photo.id}_${photoDetail.photo.secret}_z.jpg")
    }

    @Test
    fun `when building url from photo detail and large 1024 request size, return the correct url`() = runTest {
        val photoDetail = mockk<FlickrPhotoDetail>()
        val photoDetailContent = mockk<FlickrPhotoDetailContent>()
        every { photoDetailContent.id } returns "@#%$@#$2423"
        every { photoDetailContent.secret } returns "@#%$@#$"
        every { photoDetailContent.serverId} returns "6136874"
        every { photoDetailContent.farmId } returns 12
        every { photoDetail.photo } returns photoDetailContent
        val url = urlBuilderUseCase.buildUrl(photoDetail, RequestSize.Large1024)
        assertEquals(url, "https://farm${photoDetail.photo.farmId}.staticflickr.com/${photoDetail.photo.serverId}/${photoDetail.photo.id}_${photoDetail.photo.secret}_b.jpg")
    }

    @Test
    fun `when building url from photo detail and thumbnail 75 square request size, return the correct url`() = runTest {
        val photoDetail = mockk<FlickrPhotoDetail>()
        val photoDetailContent = mockk<FlickrPhotoDetailContent>()
        every { photoDetailContent.id } returns "@#%$@#$2423"
        every { photoDetailContent.secret } returns "@#%$@#$"
        every { photoDetailContent.serverId} returns "6136874"
        every { photoDetailContent.farmId } returns 12
        every { photoDetail.photo } returns photoDetailContent
        val url = urlBuilderUseCase.buildUrl(photoDetail, RequestSize.Thumbnail75Square)
        assertEquals(url, "https://farm${photoDetail.photo.farmId}.staticflickr.com/${photoDetail.photo.serverId}/${photoDetail.photo.id}_${photoDetail.photo.secret}_s.jpg")
    }

    @Test
    fun `when building url from photo detail and thumbnail 150 square request size, return the correct url`() = runTest {
        val photoDetail = mockk<FlickrPhotoDetail>()
        val photoDetailContent = mockk<FlickrPhotoDetailContent>()
        every { photoDetailContent.id } returns "@#%$@#$2423"
        every { photoDetailContent.secret } returns "@#%$@#$"
        every { photoDetailContent.serverId} returns "6136874"
        every { photoDetailContent.farmId } returns 12
        every { photoDetail.photo } returns photoDetailContent
        val url = urlBuilderUseCase.buildUrl(photoDetail, RequestSize.Thumbnail150Square)
        assertEquals(url, "https://farm${photoDetail.photo.farmId}.staticflickr.com/${photoDetail.photo.serverId}/${photoDetail.photo.id}_${photoDetail.photo.secret}_q.jpg")
    }
}