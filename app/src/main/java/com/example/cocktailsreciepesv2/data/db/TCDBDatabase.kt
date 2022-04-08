package com.example.cocktailsreciepesv2.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.cocktailsreciepesv2.domain.model.DrinkListElement
import com.example.cocktailsreciepesv2.domain.model.DrinkFavorite

@Database(entities = [DrinkListElement::class, DrinkFavorite::class],
version = 1,
exportSchema = false)
abstract class TCDBDatabase : RoomDatabase(){
    abstract fun drinkListDao() : DrinkListDao
    abstract fun drinkFavoriteDao(): DrinkFavoriteDao
}