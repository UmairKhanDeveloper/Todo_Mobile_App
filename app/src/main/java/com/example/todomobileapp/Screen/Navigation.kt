package com.example.todomobileapp.Screen

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.HomeScreen.route) {
        composable(Screen.HomeScreen.route){ HomeScreen(navController)}
        composable(Screen.DetailScreen.route){ DetailScreen(navController)}


    }

}

sealed class Screen(val route: String, val title: String){
    object HomeScreen: Screen("HomeScreen","HomeScreen")
    object DetailScreen: Screen("DetailScreen","DetailScreen")
}