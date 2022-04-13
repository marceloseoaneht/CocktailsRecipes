package com.example.cocktailsreciepesv2.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cocktailsreciepesv2.domain.model.CustomError
import com.example.cocktailsreciepesv2.domain.model.Resource
import com.example.cocktailsreciepesv2.domain.usecase.DrinkFavoriteInteractor
import com.example.cocktailsreciepesv2.domain.usecase.DrinkInfoInteractor
import com.example.cocktailsreciepesv2.presentation.util.DrinkInfoScreenState
import com.example.cocktailsreciepesv2.presentation.util.toResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DrinkInfoViewModel(
    private val drinkInfoInteractor: DrinkInfoInteractor,
    private val drinkFavoriteInteractor: DrinkFavoriteInteractor
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
            drinkFavoriteInteractor.addDrinkToFavorite(drinkId)
            viewModelState.update { it.copy(isFavorite = true) }
        }
    }

    fun deleteDrinkFromFavorite(drinkId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            drinkFavoriteInteractor.deleteDrinkFromFavorite(drinkId)
            viewModelState.update { it.copy(isFavorite = false) }
        }
    }

    private fun isDrinkFavorite(drinkId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val res = drinkFavoriteInteractor.isDrinkFavorite(drinkId)
            viewModelState.update { it.copy(isFavorite = res) }
        }
    }

    fun getDrinkInfo(drinkId: Int) {
        isDrinkFavorite(drinkId)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val res = drinkInfoInteractor.getDrinkInfo(drinkId)
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