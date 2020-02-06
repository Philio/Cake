package com.waracle.cakelist.data.repository

import com.waracle.cakelist.data.remote.Cake
import com.waracle.cakelist.data.remote.CakeService

class CakeRepository(private val cakeService: CakeService) {

    suspend fun getUniqueCakes(): List<Cake> {
        return cakeService.getCakes()
            .distinct()
    }
}