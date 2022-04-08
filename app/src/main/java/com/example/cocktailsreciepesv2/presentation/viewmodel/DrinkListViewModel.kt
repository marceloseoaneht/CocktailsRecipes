package com.example.cocktailsreciepesv2.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cocktailsreciepesv2.domain.model.CustomError
import com.example.cocktailsreciepesv2.domain.repository.DrinkFavoriteRepository
import com.example.cocktailsreciepesv2.domain.repository.DrinkListRepository
import com.example.cocktailsreciepesv2.presentation.util.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class DrinkListViewModel(
    private val drinkListRepository: DrinkListRepository,
    private val drinkFavoriteRepository: DrinkFavoriteRepository,
) : ViewModel() {

    private val drinkListViewModelState: MutableStateFlow<DrinkListViewModelState> =
        MutableStateFlow(DrinkListViewModelState(isLoading = true))

    val uiState: StateFlow<DrinkListUiState> = drinkListViewModelState
        .map { it.toUiState() }
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            drinkListViewModelState.value.toUiState()
        )

    init {
        getDrinks()
        getFavorites()
    }

    fun search(query: String) {
        drinkListViewModelState.update { it.copy(searchString = query) }
    }

    fun showSearchBar(value: Boolean) {
        if (!value) {
            search("")
        }
        drinkListViewModelState.update { it.copy(isSearching = value) }
    }

    private fun getFavorites() {
        viewModelScope.launch(Dispatchers.IO) {
            drinkFavoriteRepository.getFavorites().collect { list ->
                drinkListViewModelState.update { viewModelState ->
                    viewModelState.copy(favorites = list.map { it.drinkId }.toSet())
                }
            }
        }
    }

    fun addDrinkToFavorite(drinkId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            drinkFavoriteRepository.addFavorite(drinkId)
        }
    }

    fun deleteDrinkFromFavorite(drinkId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            drinkFavoriteRepository.deleteFavorite(drinkId)
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
            drinkListRepository.getDrinks().collect {
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
                drinkListRepository.updateDrinks()
            } catch (e: Exception) {
                drinkListViewModelState.update { it.copy(error = CustomError.DATA_ERROR.toResource()) }
            }
        }
    }

}