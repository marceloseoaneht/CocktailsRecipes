package com.example.cocktailsreciepesv2.domain.repository

import com.example.cocktailsreciepesv2.domain.model.DrinkFavorite
import com.example.cocktailsreciepesv2.domain.model.Resource
import kotlinx.coroutines.flow.Flow

interface DrinkFavoriteRepository {
    suspend fun addFavorite(drinkId: Int)
    suspend fun deleteFavorite(drinkId: Int)
    suspend fun isFavorite(drinkId: Int): Boolean
    suspend fun getFavorites(): Flow<List<DrinkFavorite>>
}