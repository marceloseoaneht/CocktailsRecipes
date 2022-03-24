package com.example.cocktailsreciepesv2.presentation.util

import com.example.cocktailsreciepesv2.R
import com.example.cocktailsreciepesv2.domain.model.CustomError

fun CustomError.toResource(): Int {
    return when (this) {
        CustomError.DATA_ERROR -> R.string.data_error
    }
}