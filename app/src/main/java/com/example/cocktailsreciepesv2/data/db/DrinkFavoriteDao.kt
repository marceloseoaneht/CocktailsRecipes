package com.example.cocktailsreciepesv2.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cocktailsreciepesv2.domain.model.DrinkFavorite
import kotlinx.coroutines.flow.Flow

@Dao
interface DrinkFavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addFavorite(drink: DrinkFavorite)

    @Query("DELETE FROM drink_favorite WHERE drinkId = :drinkId")
    fun deleteFavorite(drinkId: Int)

    @Query("SELECT * FROM drink_favorite WHERE drinkId = :drinkId")
    fun getFavorite(drinkId: Int): DrinkFavorite?

    @Query("SELECT * FROM drink_favorite")
    fun getFavorites(): Flow<List<DrinkFavorite>>
}