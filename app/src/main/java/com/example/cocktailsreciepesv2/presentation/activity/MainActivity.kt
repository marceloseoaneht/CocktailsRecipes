package com.example.cocktailsreciepesv2.presentation.activity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.cocktailsreciepesv2.presentation.compose.DrinkDetailScreen
import com.example.cocktailsreciepesv2.presentation.compose.DrinkListScreen

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "list") {
                composable(route = "list") {
                    DrinkListScreen(navController)
                }
                composable(
                    route = "info/{drinkId}",
                    arguments = listOf(navArgument("drinkId") { type = NavType.IntType })
                ) {
                    val id = it.arguments?.getInt("drinkId")
                    DrinkDetailScreen(id, navController)
                }
            }
        }
    }
}