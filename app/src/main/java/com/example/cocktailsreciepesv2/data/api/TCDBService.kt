package com.example.cocktailsreciepesv2.data.api

import com.example.cocktailsreciepesv2.data.model.APIDrinkInfo
import com.example.cocktailsreciepesv2.data.model.APIDrinkList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TCDBService {
    @GET("v1/1/filter.php")
    suspend fun getDrinkList(
        @Query("g") glass: String,
    ): Response<APIDrinkList>

    @GET("v1/1/lookup.php")
    suspend fun getDrinkInfo(
        @Query("i") drinkId: Int,
    ): Response<APIDrinkInfo>
}