package com.example.cocktailsreciepesv2.presentation.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.cocktailsreciepesv2.R
import com.example.cocktailsreciepesv2.domain.model.DrinkListElementWithFavorite
import com.example.cocktailsreciepesv2.presentation.util.ViewState
import com.example.cocktailsreciepesv2.presentation.viewmodel.DrinkListViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import org.koin.androidx.compose.getViewModel

@Composable
fun DrinkListScreen(navController: NavHostController) {
    val drinkListViewModel = getViewModel<DrinkListViewModel>()
    val state by drinkListViewModel.uiState.collectAsState()

    Scaffold(
        backgroundColor = Color.Black,
        topBar = {
            MainAppBar(
                searchWidgetState = state.isSearching,
                onSearchClicked = { drinkListViewModel.search(it) },
                onCloseClicked = { drinkListViewModel.showSearchBar(false) },
                openSearch = { drinkListViewModel.showSearchBar(true) },
                stringSearch = state.searchString,
                onGridClicked = { drinkListViewModel.enableGridView() },
                onListClicked = { drinkListViewModel.enableListView() }
            )
        },
        content = {
            SwipeRefresh(state = rememberSwipeRefreshState(state.isRefreshing),
                onRefresh = { drinkListViewModel.updateDrinks() },
                Modifier
                    .fillMaxHeight()
            ) {
                DrinkListLazyColumn(
                    state.result,
                    navController,
                    onFavClicked = { drinkListViewModel.addDrinkToFavorite(it) },
                    onFavUnClicked = { drinkListViewModel.deleteDrinkFromFavorite(it) },
                    state.viewState
                )
            }
            Box(
                Modifier
                    .fillMaxSize()
                    .background(color = Color.Transparent),
                contentAlignment = Alignment.Center
            ) {
                if (state.isLoading) {
                    ProgressBar(R.color.black)
                }
            }
        }
    )
}

@Composable
fun MainAppBar(
    searchWidgetState: Boolean,
    onCloseClicked: () -> Unit,
    onSearchClicked: (String) -> Unit,
    openSearch: () -> Unit,
    stringSearch: String,
    onGridClicked: () -> Unit,
    onListClicked: () -> Unit,
) {
    if (searchWidgetState) {
        SearchAppBar(
            onCloseClicked = onCloseClicked,
            onTextChange = onSearchClicked,
            stringSearch = stringSearch
        )
    } else {
        DefaultAppBar(
            openSearch = openSearch,
            onGridClicked = onGridClicked,
            onListClicked = onListClicked
        )
    }
}

@Composable
fun DefaultAppBar(
    openSearch: () -> Unit,
    onGridClicked: () -> Unit,
    onListClicked: () -> Unit,
) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(R.string.app_name),
                color = colorResource(R.color.white)
            )
        },
        actions = {
            IconButton(onClick = { openSearch() }
            ) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Search Icon",
                    tint = Color.White
                )
            }
            IconButton(onClick = { onListClicked() }) {
                Image(
                    painter = painterResource(id = R.drawable.list_view),
                    contentDescription = "List View"
                )
            }
            IconButton(onClick = { onGridClicked() }) {
                Image(
                    painter = painterResource(id = R.drawable.grid_view),
                    contentDescription = "Grid View")
            }
        },
        backgroundColor = colorResource(id = R.color.black),
        contentColor = colorResource(id = R.color.white)
    )
}

@Composable
fun SearchAppBar(
    onCloseClicked: () -> Unit,
    onTextChange: (String) -> Unit,
    stringSearch: String,
) {
    val focusRequester = remember { FocusRequester() }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        color = Color.Black,
        contentColor = Color.White,
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester),
            value = stringSearch,
            onValueChange = { it: String -> onTextChange(it) },
            placeholder = {
                Text(
                    modifier = Modifier
                        .alpha(ContentAlpha.medium),
                    text = "Search drink",
                    color = Color.White
                )
            },
            singleLine = true,
            leadingIcon = {
                IconButton(
                    modifier = Modifier
                        .alpha(ContentAlpha.medium),
                    onClick = {}
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Icon",
                        tint = Color.White
                    )
                }
            },
            trailingIcon = {
                IconButton(
                    onClick = {
                        onCloseClicked()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close Icon",
                        tint = Color.White
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                cursorColor = Color.White.copy(alpha = ContentAlpha.medium),
                focusedIndicatorColor = Color.Black
            )
        )
    }
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
}

@Composable
fun DrinkListImage(image: String) {
    AsyncImage(
        model = image,
        contentDescription = null,
        placeholder = painterResource(R.drawable.list_cocktail_image_placeholder),
        error = painterResource(R.drawable.list_cocktail_image_placeholder),
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .width(100.dp)
            .height(100.dp)
            .padding(10.dp)
            .clip(RoundedCornerShape(8.dp))
    )
}

@Composable
fun DrinkGridImage(image: String) {
    AsyncImage(
        model = image,
        contentDescription = null,
        placeholder = painterResource(R.drawable.list_cocktail_image_placeholder),
        error = painterResource(R.drawable.list_cocktail_image_placeholder),
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxWidth()
            .height(130.dp),
        alignment = Alignment.TopEnd
    )
}

@Composable
fun DrinkListText(text: String) {
    Text(
        text = text,
        fontSize = 25.sp,
        modifier = Modifier
            .width(220.dp)
            .padding(10.dp, 5.dp)
    )
}

@Composable
fun DrinkGridText(text: String) {
    Text(
        text = text,
        fontSize = 20.sp,
        modifier = Modifier
            .padding(5.dp),
        textAlign = TextAlign.Center
    )
}

@Composable
fun CardViewDrinkList(
    drinkListElement: DrinkListElementWithFavorite,
    navController: NavHostController,
    onFavClicked: (Int) -> Unit,
    onFavUnClicked: (Int) -> Unit,
) {
    Card(
        shape = RoundedCornerShape(dimensionResource(R.dimen.item_cocktail_recipe_corner_radius)),
        backgroundColor = Color.White,
        modifier = Modifier
            .padding(10.dp, 5.dp, 10.dp, 5.dp)
            .fillMaxWidth(),
        elevation = 10.dp
    ) {
        Row(
            modifier = Modifier
                .clickable { navController.navigate("info/${drinkListElement.id}") },
            verticalAlignment = Alignment.CenterVertically
        ) {
            DrinkListImage(drinkListElement.image)
            DrinkListText(drinkListElement.name)
            Box(
                contentAlignment = Alignment.CenterEnd,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 15.dp)
            ) {
                IconButton(onClick = {
                    if (drinkListElement.favorite) {
                        onFavUnClicked(drinkListElement.id)
                    } else {
                        onFavClicked(drinkListElement.id)
                    }
                }) {
                    Icon(
                        imageVector =
                        if (drinkListElement.favorite) {
                            Icons.Filled.Favorite
                        } else {
                            Icons.Default.FavoriteBorder
                        },
                        contentDescription = "Favourite Icon",
                        tint = Color.Black,
                    )
                }
            }
        }
    }
}

@Composable
fun CardViewDrinkGrid(
    drinkListElement: DrinkListElementWithFavorite,
    navController: NavHostController,
    onFavClicked: (Int) -> Unit,
    onFavUnClicked: (Int) -> Unit,
) {
    Card(
        backgroundColor = Color.White,
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(5.dp, 5.dp),
        elevation = 10.dp
    ) {
        Column(
            modifier = Modifier
                .clickable { navController.navigate("info/${drinkListElement.id}") },
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                contentAlignment = Alignment.TopEnd
            ) {
                DrinkGridImage(drinkListElement.image)
                IconButton(
                    onClick = {
                        if (drinkListElement.favorite) {
                            onFavUnClicked(drinkListElement.id)
                        } else {
                            onFavClicked(drinkListElement.id)
                        }
                    }) {
                    Icon(
                        imageVector =
                        if (drinkListElement.favorite) {
                            Icons.Filled.Favorite
                        } else {
                            Icons.Default.FavoriteBorder
                        },
                        contentDescription = "Favourite Icon",
                        tint = Color.White,
                    )
                }
            }
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                DrinkGridText(drinkListElement.name)
            }
        }
    }
}

@Composable
fun DrinkListLazyColumn(
    drinkList: List<DrinkListElementWithFavorite>,
    navController: NavHostController,
    onFavClicked: (Int) -> Unit,
    onFavUnClicked: (Int) -> Unit,
    viewState: ViewState,
) {
    if (viewState == ViewState.LIST) {
        LazyColumn {
            items(drinkList) { drink ->
                CardViewDrinkList(drink, navController, onFavClicked, onFavUnClicked)
            }
        }
    } else {
        LazyColumn {
            items(drinkList.chunked(2)) { drink ->
                Row(horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Column(Modifier.weight(1F)) {
                        CardViewDrinkGrid(drink[0], navController, onFavClicked, onFavUnClicked)
                    }
                    Column(Modifier.weight(1F)) {
                        if (drink.size > 1) {
                            CardViewDrinkGrid(drink[1], navController, onFavClicked, onFavUnClicked)
                        }
                    }
                }
            }
        }
    }
}