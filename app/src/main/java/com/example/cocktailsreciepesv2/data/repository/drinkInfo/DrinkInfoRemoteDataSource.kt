package com.example.cocktailsreciepesv2.data.repository.drinkInfo

import com.example.cocktailsreciepesv2.data.api.TCDBService
import com.example.cocktailsreciepesv2.data.model.APIDrinkInfo

interface DrinkInfoRemoteDataSource{
    suspend fun getDrinkInfo(drinkId: Int) : APIDrinkInfo
}

class DrinkInfoRemoteDataSourceImpl(private val tcdbService: TCDBService) :
    DrinkInfoRemoteDataSource {

    override suspend fun getDrinkInfo(drinkId: Int) : APIDrinkInfo {
        val res = tcdbService.getDrinkInfo(drinkId)
        return if (res.isSuccessful) {
            res.body() ?: throw Exception("Empty value")
        } else {
            throw Exception("Retrofit error")
        }
    }

}