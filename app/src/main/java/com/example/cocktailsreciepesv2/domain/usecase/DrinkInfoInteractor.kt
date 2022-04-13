package com.example.cocktailsreciepesv2.domain.usecase

import com.example.cocktailsreciepesv2.domain.model.DrinkInfo
import com.example.cocktailsreciepesv2.domain.model.Resource
import com.example.cocktailsreciepesv2.domain.repository.DrinkFavoriteRepository
import com.example.cocktailsreciepesv2.domain.repository.DrinkInfoRepository

interface DrinkInfoInteractor {
    suspend fun getDrinkInfo(drinkId: Int): Resource<DrinkInfo>
}

class DrinkInfoInteractorImpl(
    private val drinkInfoRepository: DrinkInfoRepository,
) : DrinkInfoInteractor {

    override suspend fun getDrinkInfo(drinkId: Int): Resource<DrinkInfo> {
        return drinkInfoRepository.getDrinkInfo(drinkId)
    }

}