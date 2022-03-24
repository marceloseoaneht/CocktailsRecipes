package com.example.cocktailsreciepesv2.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.cocktailsreciepesv2.domain.model.DrinkListElement

@Database(entities = [DrinkListElement::class],
version = 1,
exportSchema = false)
abstract class TCDBDatabase : RoomDatabase(){
    abstract fun drinkListDao() : DrinkListDao
}