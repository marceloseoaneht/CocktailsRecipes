package com.example.cocktailsreciepesv2.presentation.util

import com.example.cocktailsreciepesv2.domain.model.DrinkFavorite
import com.example.cocktailsreciepesv2.domain.model.DrinkInfo
import com.example.cocktailsreciepesv2.domain.model.Ingredient

data class DrinkInfoScreenState(
    val result: DrinkInfo = DrinkInfo(0, "", "", "", emptyList()),
    val favorite: DrinkFavorite = DrinkFavorite(0),
    val isLoading: Boolean = false,
    val isFavorite: Boolean = false,
    val error: Int = 0
)
