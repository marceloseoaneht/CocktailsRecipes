package com.example.cocktailsreciepesv2.data.model

import com.example.cocktailsreciepesv2.domain.model.DrinkListElement


data class APIDrinkList(
    val drinks: List<DrinkListDetail>,
)


fun APIDrinkList.drinkListToDomain(): List<DrinkListElement> {
    val res = mutableListOf<DrinkListElement>()

    for (drink in drinks) {
        res.add(DrinkListElement(
            name = drink.strDrink,
            image = drink.strDrinkThumb,
            id = drink.idDrink)
        )
    }

    return res
}

data class DrinkListDetail(
    val strDrink: String,
    val strDrinkThumb: String,
    val idDrink: Int,
)
