package com.example.cocktailsreciepesv2.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cocktailsreciepesv2.domain.model.CustomError
import com.example.cocktailsreciepesv2.domain.usecase.DrinkFavoriteInteractor
import com.example.cocktailsreciepesv2.domain.usecase.DrinkListInteractor
import com.example.cocktailsreciepesv2.presentation.util.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class DrinkListViewModel(
    private val drinkListInteractor: DrinkListInteractor,
    private val drinkFavoriteInteractor: DrinkFavoriteInteractor
) : ViewModel() {

    private val drinkListViewModelState: MutableStateFlow<DrinkListViewModelState> =
        MutableStateFlow(DrinkListViewModelState(isLoading = true))

    val uiState: StateFlow<DrinkListViewModelState> = drinkListViewModelState
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            drinkListViewModelState.value
        )

    init {
        getDrinks()
    }

    fun search(query: String) {
        drinkListViewModelState.update { it.copy(searchString = query) }
        drinkListInteractor.updateSearchParameter(query)
    }

    fun showSearchBar(value: Boolean) {
        if (!value) {
            search("")
        }
        drinkListViewModelState.update { it.copy(isSearching = value) }
    }

    fun addDrinkToFavorite(drinkId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            drinkFavoriteInteractor.addDrinkToFavorite(drinkId)
        }
    }

    fun deleteDrinkFromFavorite(drinkId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            drinkFavoriteInteractor.deleteDrinkFromFavorite(drinkId)
        }
    }

    fun enableGridView() {
        drinkListViewModelState.update { it.copy(viewState = ViewState.GRID) }
    }

    fun enableListView() {
        drinkListViewModelState.update { it.copy(viewState = ViewState.LIST) }
    }

    private fun getDrinks() {
        viewModelScope.launch(Dispatchers.IO) {
            drinkListInteractor.getDrinks().collect {
                it.checkResult(
                    onSuccess = { list ->
                        drinkListViewModelState.update {
                            it.copy(result = list,
                                isLoading = false,
                                isRefreshing = false)
                        }
                    },
                    onError = {
                        val error = it
                        drinkListViewModelState.update {
                            it.copy(error = error.toResource(),
                                isLoading = false,
                                isRefreshing = false)
                        }
                    }
                )
            }.runCatching {
                drinkListViewModelState.update {
                    it.copy(error = CustomError.DATA_ERROR.toResource(),
                        isLoading = false,
                        isRefreshing = false)
                }
            }
        }
    }

    fun updateDrinks() {
        drinkListViewModelState.update { it.copy(isRefreshing = true) }
        viewModelScope.launch(Dispatchers.IO) {
            try {
                drinkListInteractor.updateDrinks()
            } catch (e: Exception) {
                drinkListViewModelState.update { it.copy(error = CustomError.DATA_ERROR.toResource()) }
            }
        }
    }

}