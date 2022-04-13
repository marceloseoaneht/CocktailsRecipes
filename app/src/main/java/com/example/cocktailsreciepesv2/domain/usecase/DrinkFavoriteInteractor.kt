package com.example.cocktailsreciepesv2.domain.usecase

import com.example.cocktailsreciepesv2.domain.model.DrinkFavorite
import com.example.cocktailsreciepesv2.domain.repository.DrinkFavoriteRepository
import kotlinx.coroutines.flow.Flow

interface DrinkFavoriteInteractor {
    suspend fun addDrinkToFavorite(drinkId: Int)
    suspend fun deleteDrinkFromFavorite(drinkId: Int)
    suspend fun isDrinkFavorite(drinkId: Int): Boolean
    suspend fun getFavorites(): Flow<List<DrinkFavorite>>
}

class DrinkFavoriteInteractorImpl(private val drinkFavoriteRepository: DrinkFavoriteRepository) :
    DrinkFavoriteInteractor {
    override suspend fun addDrinkToFavorite(drinkId: Int) {
        drinkFavoriteRepository.addFavorite(drinkId)
    }

    override suspend fun deleteDrinkFromFavorite(drinkId: Int) {
        drinkFavoriteRepository.deleteFavorite(drinkId)
    }

    override suspend fun isDrinkFavorite(drinkId: Int): Boolean {
        return drinkFavoriteRepository.isFavorite(drinkId)
    }

    override suspend fun getFavorites(): Flow<List<DrinkFavorite>> {
        return drinkFavoriteRepository.getFavorites()
    }
}