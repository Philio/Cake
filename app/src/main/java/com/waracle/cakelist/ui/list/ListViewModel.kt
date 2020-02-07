package com.waracle.cakelist.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.waracle.cakelist.data.model.Cake
import com.waracle.cakelist.data.repository.CakeRepository

class ListViewModel(private val cakeRepository: CakeRepository) : ViewModel() {

    val cakes: LiveData<Result> = liveData {
        emit(Result.Loading)
        try {
            val cakes = cakeRepository.getUniqueCakes().sortedBy { it.title }
            emit(Result.Success(cakes))
        } catch (exception: Exception) {
            emit(Result.Error(exception))
        }
    }
}

sealed class Result {
    object Loading : Result()
    data class Success(val cakes: List<Cake>) : Result()
    data class Error(val throwable: Throwable) : Result()
}