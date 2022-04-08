package com.example.cocktailsreciepesv2.data.repository.drinkFavorite

import android.util.Log
import com.example.cocktailsreciepesv2.domain.model.CustomError
import com.example.cocktailsreciepesv2.domain.model.DrinkFavorite
import com.example.cocktailsreciepesv2.domain.model.Resource
import com.example.cocktailsreciepesv2.domain.repository.DrinkFavoriteRepository
import kotlinx.coroutines.flow.Flow
import java.lang.Exception

class DrinkFavoriteRepositoryImpl(private val drinkFavoriteLocalDataSource: DrinkFavoriteLocalDataSource) :
    DrinkFavoriteRepository {
    override suspend fun addFavorite(drinkId: Int) {
        try {
            drinkFavoriteLocalDataSource.addFavoriteToDB(drinkId)
        } catch (e: Exception) {
            Log.e("Error", CustomError.ADD_FAV_ERROR.toString())
        }
    }

    override suspend fun deleteFavorite(drinkId: Int) {
        try {
            drinkFavoriteLocalDataSource.deleteFavoriteFromDB(drinkId)
        } catch (e: Exception) {
            Log.e("Error", CustomError.DEL_FAV_ERROR.toString())
        }
    }

    override suspend fun isFavorite(drinkId: Int): Boolean {
        val res = drinkFavoriteLocalDataSource.getFavoriteFromDB(drinkId)
        return res != null
    }

    override suspend fun getFavorites(): Flow<List<DrinkFavorite>> {
        return drinkFavoriteLocalDataSource.getAllFavorites()
    }
}