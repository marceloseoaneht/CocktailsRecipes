package com.example.cocktailsreciepesv2.presentation.util

import com.example.cocktailsreciepesv2.presentation.model.PDrinkElement

data class DrinkListUiState(
    val result: List<PDrinkElement> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val isSearching: Boolean = false,
    val searchString: String = "",
    val error: Int = 0,
    val viewState: ViewState = ViewState.LIST
)