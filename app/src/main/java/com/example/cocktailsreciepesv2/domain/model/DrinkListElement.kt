package com.example.cocktailsreciepesv2.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "drink_list_table")
data class DrinkListElement(
    val name: String,
    val image: String,
    @PrimaryKey
    val id: Int,
) {
    fun search(query: String): Boolean {
        return name.contains(query, true)
    }
}