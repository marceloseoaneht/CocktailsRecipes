package com.example.cocktailsreciepesv2.presentation.viewmodel

import androidx.lifecycle.*
import com.example.cocktailsreciepesv2.domain.model.CustomError
import com.example.cocktailsreciepesv2.domain.model.DrinkFavorite
import com.example.cocktailsreciepesv2.domain.repository.DrinkInfoRepository
import com.example.cocktailsreciepesv2.domain.model.Resource
import com.example.cocktailsreciepesv2.domain.repository.DrinkFavoriteRepository
import com.example.cocktailsreciepesv2.presentation.util.DrinkInfoScreenState
import com.example.cocktailsreciepesv2.presentation.util.toResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.lang.Exception

class DrinkInfoViewModel(
    private val drinkInfoRepository: DrinkInfoRepository,
    private val drinkFavoriteRepository: DrinkFavoriteRepository,
) : ViewModel() {

    private val viewModelState = MutableStateFlow(DrinkInfoScreenState(isLoading = true))

    val uiState = viewModelState
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.value
        )

    fun addDrinkToFavorite(drinkId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            drinkFavoriteRepository.addFavorite(drinkId)
            viewModelState.update { it.copy(isFavorite = true) }
        }
    }

    fun deleteDrinkFromFavorite(drinkId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            drinkFavoriteRepository.deleteFavorite(drinkId)
            viewModelState.update { it.copy(isFavorite = false) }
        }
    }

    private fun isDrinkFavorite(drinkId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val res = drinkFavoriteRepository.isFavorite(drinkId)
            viewModelState.update { it.copy(isFavorite = res) }
        }
    }

    fun getDrinkInfo(drinkId: Int) {
        isDrinkFavorite(drinkId)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val res = drinkInfoRepository.getDrinkInfo(drinkId)
                when (res) {
                    is Resource.Success -> {
                        viewModelState.update { it.copy(result = res.data) }
                    }
                    is Resource.Error -> {
                        viewModelState.update { it.copy(error = res.error.toResource()) }
                    }
                }

            } catch (e: Exception) {
                viewModelState.update { it.copy(error = CustomError.DATA_ERROR.toResource()) }
            }
            viewModelState.update { it.copy(isLoading = false) }
        }
    }

}