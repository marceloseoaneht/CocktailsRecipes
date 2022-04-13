package com.example.cocktailsreciepesv2.domain.model

data class DrinkListElementWithFavorite(
    val name: String,
    val image: String,
    val id: Int,
    val favorite: Boolean
)