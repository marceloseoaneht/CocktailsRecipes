package com.example.cocktailsreciepesv2.domain.usecase

import com.example.cocktailsreciepesv2.domain.model.*
import com.example.cocktailsreciepesv2.domain.repository.DrinkListRepository
import kotlinx.coroutines.flow.*

interface DrinkListInteractor {
    suspend fun getDrinks(): Flow<Resource<List<DrinkListElementWithFavorite>>>
    suspend fun updateDrinks()
    fun updateSearchParameter(queryString: String)
}

class DrinkListInteractorImpl(
    private val drinkListRepository: DrinkListRepository,
    private val drinkFavoriteInteractor: DrinkFavoriteInteractor,
) : DrinkListInteractor, DrinkFavoriteInteractor by drinkFavoriteInteractor {
    private var _queryString = MutableStateFlow("")


    override suspend fun getDrinks(): Flow<Resource<List<DrinkListElementWithFavorite>>> =
        drinkListRepository.getDrinks().combine(_queryString) { drinkList: List<DrinkListElement>, queryString: String ->
            drinkList.filter { it.search(queryString) }.sortedBy { it.name }
        }.combine(drinkFavoriteInteractor.getFavorites().map { drinkFavoriteList: List<DrinkFavorite> ->
            drinkFavoriteList.map {
                it.drinkId
            }
        }) { drinkListFiltered: List<DrinkListElement>, favoritesIDList: List<Int> ->
            Resource.Success(drinkListFiltered.map {
                DrinkListElementWithFavorite(
                    name = it.name,
                    image = it.image,
                    id = it.id,
                    favorite = it.id in favoritesIDList
                )
            })
        }.catch {
            Resource.Error(CustomError.DATA_ERROR)
        }


    override suspend fun updateDrinks() {
        return drinkListRepository.updateDrinks()
    }

    override fun updateSearchParameter(queryString: String) {
        _queryString.value = queryString
    }

}