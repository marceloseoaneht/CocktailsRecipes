package com.example.cocktailsreciepesv2.data.repository.drinkList

import android.util.Log
import com.example.cocktailsreciepesv2.data.model.drinkListToDomain
import com.example.cocktailsreciepesv2.domain.model.CustomError
import com.example.cocktailsreciepesv2.domain.model.DrinkListElement
import com.example.cocktailsreciepesv2.domain.repository.DrinkListRepository
import com.example.cocktailsreciepesv2.domain.model.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.lang.Exception

class DrinkListRepositoryImpl(
    private val drinkListRemoteDataSource: DrinkListRemoteDataSource,
    private val drinkListLocalDataSource: DrinkListLocalDataSource,
) : DrinkListRepository {

    private var firstTime = true

    override suspend fun getDrinks(): Flow<List<DrinkListElement>> {
        if (firstTime) {
            CoroutineScope(Dispatchers.IO).launch {
                val res = getDrinksFromAPI()
                if (res != null) {
                    updateLocalDB(res)
                }
            }
            firstTime = false
        }
        return drinkListLocalDataSource.getDrinksFromDB()
    }

    override suspend fun updateDrinks() {
        val res = getDrinksFromAPI() ?: throw Exception("Remote error")
        updateLocalDB(res)
    }

    override suspend fun searchDrinks(searchQuery: String): Flow<Resource<List<DrinkListElement>>> {
        return drinkListLocalDataSource.getSearchedDrinks(searchQuery)
            .map<List<DrinkListElement>, Resource<List<DrinkListElement>>> {
                Resource.Success(it)
            }.catch {
                emit(Resource.Error(CustomError.DATA_ERROR))
            }
    }

    private suspend fun getDrinksFromAPI(): List<DrinkListElement>? {
        return try {
            drinkListRemoteDataSource.getDrinks().drinkListToDomain()
        } catch (e: Exception) {
            Log.e("Error", e.toString())
            null
        }
    }

    private fun updateLocalDB(list: List<DrinkListElement>) {
        drinkListLocalDataSource.clearDB()
        drinkListLocalDataSource.saveDrinksToDB(list)
    }

}