package com.example.cocktailsreciepesv2.presentation.util

import com.example.cocktailsreciepesv2.domain.model.DrinkListElementWithFavorite

data class DrinkListViewModelState(
    val result: List<DrinkListElementWithFavorite> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val isSearching: Boolean = false,
    val searchString: String = "",
    val error: Int = 0,
    val viewState: ViewState = ViewState.LIST
)

enum class ViewState {
    GRID,
    LIST
}

