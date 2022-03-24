package com.example.cocktailsreciepesv2.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cocktailsreciepesv2.data.model.DrinkInfoDetail

@Dao
interface DrinkInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveDrinkInfo(drinkInfo : List<DrinkInfoDetail>)

    @Query("DELETE FROM drink_info_table")
    suspend fun deleteAllDrinksInfo()

    @Query("SELECT * FROM drink_info_table")
    suspend fun getDrinkInfo() : List<DrinkInfoDetail>
}