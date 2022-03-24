package com.example.cocktailsreciepesv2.data.repository.drinkList

import com.example.cocktailsreciepesv2.data.db.DrinkListDao
import com.example.cocktailsreciepesv2.domain.model.DrinkListElement
import kotlinx.coroutines.flow.Flow

interface DrinkListLocalDataSource {
    suspend fun getDrinksFromDB(): Flow<List<DrinkListElement>>
    fun saveDrinksToDB(drinks: List<DrinkListElement>)
    fun clearDB()
    fun getSearchedDrinks(searchQuery: String): Flow<List<DrinkListElement>>
}

class DrinkListLocalDataSourceImpl(private val drinkListDao: DrinkListDao) :
    DrinkListLocalDataSource {

    override suspend fun getDrinksFromDB(): Flow<List<DrinkListElement>> = drinkListDao.getDrinkList()

    override fun saveDrinksToDB(drinks: List<DrinkListElement>) {
        drinkListDao.saveDrinkList(drinks)
    }

    override fun clearDB() {
        drinkListDao.deleteAllDrinks()
    }

    override fun getSearchedDrinks(searchQuery: String): Flow<List<DrinkListElement>> = drinkListDao.getDrinksByName(searchQuery)

}