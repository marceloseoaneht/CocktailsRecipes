package com.example.cocktailsreciepesv2.data.model

import com.example.cocktailsreciepesv2.domain.model.DrinkInfo
import com.example.cocktailsreciepesv2.domain.model.Ingredient

data class APIDrinkInfo(
    val drinks: List<DrinkInfoDetail>,
)

fun APIDrinkInfo.drinkInfoToDomain(): DrinkInfo {
    val aux = this.drinks.first()

    val ingredientsList = arrayOf(aux.strIngredient1,
        aux.strIngredient2,
        aux.strIngredient3,
        aux.strIngredient4,
        aux.strIngredient5,
        aux.strIngredient6,
        aux.strIngredient7,
        aux.strIngredient8,
        aux.strIngredient9,
        aux.strIngredient10,
        aux.strIngredient11,
        aux.strIngredient12,
        aux.strIngredient13,
        aux.strIngredient14,
        aux.strIngredient15)

    val measuresList = arrayOf(aux.strMeasure1,
        aux.strMeasure2,
        aux.strMeasure3,
        aux.strMeasure4,
        aux.strMeasure5,
        aux.strMeasure6,
        aux.strMeasure7,
        aux.strMeasure8,
        aux.strMeasure9,
        aux.strMeasure10,
        aux.strMeasure11,
        aux.strMeasure12,
        aux.strMeasure13,
        aux.strMeasure14,
        aux.strMeasure15)

    var ingredients = mutableListOf<Ingredient>()
    ingredientsList.zip(measuresList).forEach { pair ->
        if (pair.first != null && pair.second != null) {
            ingredients.add(Ingredient(pair.first, pair.second))
        }
    }

    return DrinkInfo(
        id = aux.idDrink,
        instructions = aux.strInstructions,
        name = aux.strDrink,
        image = aux.strDrinkThumb,
        ingredients = ingredients
    )

}

data class DrinkInfoDetail(
    val idDrink: Int,
    val strInstructions: String,
    val strDrink: String,
    val strDrinkThumb: String,
    val strIngredient1: String,
    val strIngredient2: String,
    val strIngredient3: String,
    val strIngredient4: String,
    val strIngredient5: String,
    val strIngredient6: String,
    val strIngredient7: String,
    val strIngredient8: String,
    val strIngredient9: String,
    val strIngredient10: String,
    val strIngredient11: String,
    val strIngredient12: String,
    val strIngredient13: String,
    val strIngredient14: String,
    val strIngredient15: String,
    val strMeasure1: String,
    val strMeasure2: String,
    val strMeasure3: String,
    val strMeasure4: String,
    val strMeasure5: String,
    val strMeasure6: String,
    val strMeasure7: String,
    val strMeasure8: String,
    val strMeasure9: String,
    val strMeasure10: String,
    val strMeasure11: String,
    val strMeasure12: String,
    val strMeasure13: String,
    val strMeasure14: String,
    val strMeasure15: String,
)
