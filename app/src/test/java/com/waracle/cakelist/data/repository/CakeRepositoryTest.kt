package com.waracle.cakelist.data.repository

import com.waracle.cakelist.data.remote.Cake
import com.waracle.cakelist.data.remote.CakeService
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class CakeRepositoryTest {

    private val cakeService: CakeService = mockk()

    private val subject = CakeRepository(cakeService)

    private val cake = Cake("Title", "Desc", "Image")

    @Before
    fun setup() {
        coEvery { cakeService.getCakes() } returns listOf(cake, cake, cake)
    }

    @Test
    fun `Given getUniqueCakes is called When response contains duplicates Then duplicates are removed`() {
        var result = runBlocking { subject.getUniqueCakes() }

        assertEquals(1, result.size)
        assertEquals(listOf(cake), result)
    }
}