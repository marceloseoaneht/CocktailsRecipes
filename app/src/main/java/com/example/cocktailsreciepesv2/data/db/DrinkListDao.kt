package com.example.cocktailsreciepesv2.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cocktailsreciepesv2.domain.model.DrinkListElement
import kotlinx.coroutines.flow.Flow

@Dao
interface DrinkListDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveDrinkList(drinkList : List<DrinkListElement>)

    @Query("DELETE FROM drink_list_table")
    fun deleteAllDrinks()

    @Query("SELECT * FROM drink_list_table")
    fun getDrinkList() : Flow<List<DrinkListElement>>

    @Query("SELECT * FROM drink_list_table WHERE name LIKE :searchQuery")
    fun getDrinksByName(searchQuery: String): Flow<List<DrinkListElement>>
}