package com.example.cocktailsreciepesv2.presentation.util

import com.example.cocktailsreciepesv2.domain.model.DrinkListElement
import com.example.cocktailsreciepesv2.presentation.model.PDrinkElement


data class DrinkListViewModelState(
    val result: List<DrinkListElement> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val isSearching: Boolean = false,
    val favorites: Set<Int> = emptySet(),
    val searchString: String = "",
    val error: Int = 0,
    val viewState: ViewState = ViewState.LIST
)

enum class ViewState {
    GRID,
    LIST
}

fun DrinkListViewModelState.toUiState(): DrinkListUiState {
    return DrinkListUiState(
        result = result.map {
            PDrinkElement(
                name = it.name,
                image = it.image,
                id = it.id,
                favorite = it.id in favorites)
        }.filter { it.name.contains(searchString, true) }.sortedBy { it.name },
        isLoading = isLoading,
        isRefreshing = isRefreshing,
        isSearching = isSearching,
        searchString = searchString,
        error = error,
        viewState = viewState
    )
}

