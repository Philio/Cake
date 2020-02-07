package com.waracle.cakelist.data.repository

import com.waracle.cakelist.data.model.Cake
import com.waracle.cakelist.data.remote.CakeService
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class CakeRepositoryTest {

    private val cakeService: CakeService = mockk()

    private val subject = CakeRepository(cakeService)

    private val cake = mockk<Cake>()

    @Test
    fun `Given web service contains duplicates When getUniqueCakes is called Then duplicates are removed`() {
        coEvery { cakeService.getCakes() } returns listOf(cake, cake, cake)

        val result = runBlocking { subject.getUniqueCakes() }

        assertEquals(1, result.size)
        assertEquals(listOf(cake), result)
    }
}