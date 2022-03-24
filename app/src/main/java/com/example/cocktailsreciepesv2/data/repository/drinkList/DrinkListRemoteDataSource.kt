package com.example.cocktailsreciepesv2.data.repository.drinkList

import com.example.cocktailsreciepesv2.data.DataConstants
import com.example.cocktailsreciepesv2.data.api.TCDBService
import com.example.cocktailsreciepesv2.data.model.APIDrinkList

interface DrinkListRemoteDataSource {
    suspend fun getDrinks(): APIDrinkList
}

class DrinkListRemoteDataSourceImpl(private val tcdbService: TCDBService) :
    DrinkListRemoteDataSource {

    override suspend fun getDrinks(): APIDrinkList {
        val res = tcdbService.getDrinkList(DataConstants.COCKTAILS_QUERY)
        return if (res.isSuccessful) {
            res.body() ?: throw Exception("Empty value")
        } else {
            throw Exception("Retrofit error")
        }
    }

}