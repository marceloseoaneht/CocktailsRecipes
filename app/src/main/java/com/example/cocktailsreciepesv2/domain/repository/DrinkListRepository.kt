package com.example.cocktailsreciepesv2.domain.repository

import com.example.cocktailsreciepesv2.domain.model.DrinkListElement
import com.example.cocktailsreciepesv2.domain.model.Resource
import kotlinx.coroutines.flow.Flow

interface DrinkListRepository {
    suspend fun getDrinks(): Flow<Resource<List<DrinkListElement>>>
    suspend fun updateDrinks()
    suspend fun searchDrinks(searchQuery: String): Flow<Resource<List<DrinkListElement>>>
}