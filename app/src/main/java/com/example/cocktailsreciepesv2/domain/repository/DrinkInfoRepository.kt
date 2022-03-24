package com.example.cocktailsreciepesv2.domain.repository

import com.example.cocktailsreciepesv2.domain.model.DrinkInfo
import com.example.cocktailsreciepesv2.domain.model.Resource

interface DrinkInfoRepository {
    suspend fun getDrinkInfo(drinkId: Int): Resource<DrinkInfo>
}