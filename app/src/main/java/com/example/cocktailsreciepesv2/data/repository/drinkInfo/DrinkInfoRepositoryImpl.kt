package com.example.cocktailsreciepesv2.data.repository.drinkInfo

import com.example.cocktailsreciepesv2.data.model.drinkInfoToDomain
import com.example.cocktailsreciepesv2.domain.model.CustomError
import com.example.cocktailsreciepesv2.domain.model.DrinkInfo
import com.example.cocktailsreciepesv2.domain.repository.DrinkInfoRepository
import com.example.cocktailsreciepesv2.domain.model.Resource
import java.lang.Exception

class DrinkInfoRepositoryImpl(
    private val drinkInfoRemoteDataSource: DrinkInfoRemoteDataSource,
) : DrinkInfoRepository {

    override suspend fun getDrinkInfo(drinkId: Int): Resource<DrinkInfo> {
        return try {
            val res = drinkInfoRemoteDataSource.getDrinkInfo(drinkId)
            Resource.Success(res.drinkInfoToDomain())
        } catch (e: Exception) {
            Resource.Error(CustomError.DATA_ERROR)
        }
    }

}