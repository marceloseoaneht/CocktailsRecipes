package com.example.cocktailsreciepesv2.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "drink_info_table")
data class DrinkInfo(
    @PrimaryKey
    val id: Int,
    val instructions: String,
    val name: String,
    val image: String,
    val ingredients: List<Ingredient>,
)
