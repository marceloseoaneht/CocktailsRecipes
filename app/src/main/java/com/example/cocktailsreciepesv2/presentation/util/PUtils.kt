package com.example.cocktailsreciepesv2.presentation.util

import com.example.cocktailsreciepesv2.R
import com.example.cocktailsreciepesv2.domain.model.CustomError

fun CustomError.toResource(): Int {
    return when (this) {
        CustomError.DATA_ERROR -> R.string.data_error
        CustomError.ADD_FAV_ERROR -> R.string.add_fav_error
        CustomError.DEL_FAV_ERROR -> R.string.del_fav_error
    }
}