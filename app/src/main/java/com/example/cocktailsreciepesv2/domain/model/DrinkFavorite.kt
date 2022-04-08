package com.example.cocktailsreciepesv2.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "drink_favorite")
data class DrinkFavorite(
    @PrimaryKey
    val drinkId: Int
)
