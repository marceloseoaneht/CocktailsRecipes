package com.example.cocktailsreciepesv2.presentation.compose

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.cocktailsreciepesv2.R
import com.example.cocktailsreciepesv2.domain.model.DrinkInfo
import com.example.cocktailsreciepesv2.domain.model.Ingredient
import com.example.cocktailsreciepesv2.presentation.viewmodel.DrinkInfoViewModel
import org.koin.androidx.compose.getViewModel


@Composable
fun DrinkDetailScreen(id: Int?, navController: NavHostController) {
    val drinkInfoViewModel = getViewModel<DrinkInfoViewModel>()
    val state by drinkInfoViewModel.uiState.collectAsState()
    drinkInfoViewModel.getDrinkInfo(id!!)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = state.result.name,
                        color = colorResource(R.color.white)
                    )
                },
                backgroundColor = colorResource(id = R.color.black),
                contentColor = colorResource(id = R.color.white),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, "backIcon")
                    }
                }
            )
        },
        content = {
            Box(
                Modifier
                    .fillMaxSize()
                    .background(color = colorResource(R.color.black)),
                contentAlignment = Alignment.Center
            ) {
                CardViewDrinkInfo(
                    drinkInfo = state.result,
                    onFavClicked = {
                        drinkInfoViewModel.addDrinkToFavorite(it)
                    },
                    onFavUnClicked = {
                        drinkInfoViewModel.deleteDrinkFromFavorite(it)
                    },
                    stateFavorite = state.isFavorite
                )
                if (state.isLoading) {
                    ProgressBar(R.color.black)
                }
            }
        }
    )
}


@Composable
fun CardViewDrinkInfo(
    drinkInfo: DrinkInfo,
    onFavClicked: (Int) -> Unit,
    onFavUnClicked: (Int) -> Unit,
    stateFavorite: Boolean,
) {
    val scrollState = rememberScrollState()
    Card(
        shape = RoundedCornerShape(dimensionResource(R.dimen.item_cocktail_recipe_corner_radius)),
        backgroundColor = colorResource(R.color.white),
        modifier = Modifier
            .padding(10.dp, 10.dp, 10.dp, 40.dp)
            .fillMaxHeight(),
        elevation = 10.dp
    ) {
        Column(Modifier.verticalScroll(scrollState)) {
            Box(contentAlignment = Alignment.TopEnd) {
                CardViewImage(drinkInfo.image)
                FavoriteButton(drinkInfo.id, onFavClicked, onFavUnClicked, stateFavorite)
            }
            CardViewText(stringResource(R.string.ingredients))
            DisplayIngredients(drinkInfo.ingredients)
            CardViewText(stringResource(R.string.how_to_prepare))
            CardViewText(drinkInfo.instructions)
        }
    }
}

@Composable
fun FavoriteButton(
    drinkId: Int,
    onFavClicked: (Int) -> Unit,
    onFavUnClicked: (Int) -> Unit,
    isFavorite: Boolean,
) {
    IconButton(
        modifier = Modifier
            .padding(15.dp, 15.dp)
            .size(52.dp)
            .clip(CircleShape)
            .background(Color.Black),
        onClick = {
            if (isFavorite) {
                onFavUnClicked(drinkId)
            } else {
                onFavClicked(drinkId)
            }
        }
    ) {
        Icon(
            tint = Color.White,
            imageVector =
            if (isFavorite) {
                Icons.Filled.Favorite
            } else {
                Icons.Default.FavoriteBorder
            },
            contentDescription = "Favorite icon",
            modifier = Modifier.graphicsLayer {
                scaleX = 1.5F
                scaleY = 1.5F
            }
        )

    }
}

@Composable
fun CardViewImage(image: String) {
    AsyncImage(
        model = image,
        contentDescription = null,
        placeholder = painterResource(R.drawable.list_cocktail_image_placeholder),
        error = painterResource(R.drawable.list_cocktail_image_placeholder),
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .padding(10.dp)
            .clip(RoundedCornerShape(8.dp))
    )
}

@Composable
fun CardViewText(text: String) {
    Text(
        text = text,
        fontSize = 20.sp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp, 5.dp)
    )
}

@Composable
fun IngredientListItem(ingredient: Ingredient) {
    Row(modifier = Modifier.padding(10.dp, 0.dp)) {
        Text(
            text = ingredient.measure,
            fontSize = 20.sp,
            modifier = Modifier.wrapContentSize()
        )

        Text(
            text = " - ",
            fontSize = 20.sp,
            modifier = Modifier.wrapContentSize()
        )

        Text(
            text = ingredient.name,
            fontSize = 20.sp,
            modifier = Modifier.wrapContentSize()
        )
    }
}

@Composable
fun DisplayIngredients(ingredientList: List<Ingredient>) {
    Column {
        ingredientList.forEach { ingredient ->
            IngredientListItem(ingredient)
        }
    }
}


@Composable
fun ProgressBar(color: Int) {
    CircularProgressIndicator(
        color = colorResource(color),
        strokeWidth = 6.dp,
        modifier = Modifier
            .width(100.dp)
            .height(100.dp)
    )
}