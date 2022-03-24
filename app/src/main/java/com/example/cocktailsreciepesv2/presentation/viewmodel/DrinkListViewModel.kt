package com.example.cocktailsreciepesv2.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cocktailsreciepesv2.domain.model.CustomError
import com.example.cocktailsreciepesv2.domain.model.DrinkListElement
import com.example.cocktailsreciepesv2.domain.repository.DrinkListRepository
import com.example.cocktailsreciepesv2.presentation.util.toResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.properties.Delegates
import kotlin.reflect.KProperty

class DrinkListViewModel(private val drinkListRepository: DrinkListRepository) : ViewModel() {
    //region Livedata
    val drinkListLiveData: LiveData<List<DrinkListElement>>
        get() = _drinkList
    private val _drinkList = MutableLiveData<List<DrinkListElement>>()

    val loading: LiveData<Boolean>
        get() = _loading
    private val _loading = MutableLiveData<Boolean>()

    val error: LiveData<Int>
        get() = _error
    private val _error = MutableLiveData<Int>()
    //endregion

    init {
        getDrinks()
    }

    private var searchParameter = ""
    var auxDrinkList: List<DrinkListElement> = emptyList()

    fun search(query: String) {
        searchParameter = query
        showResult()
    }

    private fun showResult() {
        _drinkList.postValue(auxDrinkList
            .filter { it.name.contains(searchParameter, true) }
            .sortedBy { it.name }
        )
    }

    private fun getDrinks() {
        _loading.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            drinkListRepository.getDrinks().collect {
                it.checkResult(
                    onSuccess = {
                        auxDrinkList = it
                        showResult()
                    },
                    onError = { _error.postValue(it.toResource()) }
                )
                _loading.postValue(false)
            }.runCatching {
                _error.postValue(CustomError.DATA_ERROR.toResource())
            }
        }
    }

    fun updateDrinks() {
        _loading.postValue(false)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                drinkListRepository.updateDrinks()
            } catch (e: Exception) {
                _error.postValue(CustomError.DATA_ERROR.toResource())
            }
        }
    }

}