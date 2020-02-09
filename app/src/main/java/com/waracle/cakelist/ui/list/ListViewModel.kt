package com.waracle.cakelist.ui.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.waracle.cakelist.data.model.Cake
import com.waracle.cakelist.data.repository.CakeRepository
import kotlinx.coroutines.launch

class ListViewModel(private val cakeRepository: CakeRepository) : ViewModel() {

    val cakes = MutableLiveData<Result>()

    init {
        refresh()
    }

    fun refresh() {
        cakes.value = Result.Loading
        viewModelScope.launch {
            // This loads very quickly and animation is not always visible. A delay can be added
            // here to make it more obvious for testing purposes, e.g. delay(2000)
            try {
                val newCakes = cakeRepository.getUniqueCakes().sortedBy { it.title }
                cakes.postValue(Result.Success(newCakes))
            } catch (exception: Exception) {
                cakes.postValue(Result.Error(exception))
            }
        }
    }
}

sealed class Result {
    object Loading : Result()
    data class Success(val cakes: List<Cake>) : Result()
    data class Error(val throwable: Throwable) : Result()
}