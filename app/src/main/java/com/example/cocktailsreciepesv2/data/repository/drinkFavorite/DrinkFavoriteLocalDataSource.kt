package com.example.cocktailsreciepesv2.data.repository.drinkFavorite

import com.example.cocktailsreciepesv2.data.db.DrinkFavoriteDao
import com.example.cocktailsreciepesv2.domain.model.DrinkFavorite
import kotlinx.coroutines.flow.Flow

interface DrinkFavoriteLocalDataSource {
    fun addFavoriteToDB(drinkId: Int)
    fun deleteFavoriteFromDB(drinkId: Int)
    fun getFavoriteFromDB(drinkId: Int): DrinkFavorite?
    fun getAllFavorites(): Flow<List<DrinkFavorite>>
}

class DrinkFavoriteLocalDataSourceImpl(private val drinkFavoriteDao: DrinkFavoriteDao) :
    DrinkFavoriteLocalDataSource {
    override fun addFavoriteToDB(drinkId: Int) {
        val drink = DrinkFavorite(drinkId)
        drinkFavoriteDao.addFavorite(drink)
    }

    override fun deleteFavoriteFromDB(drinkId: Int) {
        drinkFavoriteDao.deleteFavorite(drinkId)
    }

    override fun getFavoriteFromDB(drinkId: Int): DrinkFavorite? {
        return drinkFavoriteDao.getFavorite(drinkId)
    }

    override fun getAllFavorites(): Flow<List<DrinkFavorite>> {
        return drinkFavoriteDao.getFavorites()
    }
}