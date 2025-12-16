package com.example.todomobileapp.Screen

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.splashscreen.SplashScreen

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.SplashScreen.route) {
        composable(Screen.SplashScreen.route) { SplashScreen(navController) }
        composable(Screen.HomeScreen.route) { HomeScreen(navController) }
        composable(Screen.DetailScreen.route) { DetailScreen(navController) }
        composable(
            route = Screen.TaskDetail.route +
                    "/{id}/{title}/{notes}/{category}/{dateMillis}/{timeMillis}/{isCompleted}",
            arguments = listOf(
                navArgument("id") { type = NavType.IntType }, // Task ID
                navArgument("title") { type = NavType.StringType },
                navArgument("notes") { type = NavType.StringType; defaultValue = "" },
                navArgument("category") { type = NavType.IntType },
                navArgument("dateMillis") { type = NavType.LongType },
                navArgument("timeMillis") { type = NavType.LongType },
                navArgument("isCompleted") { type = NavType.BoolType }
            )
        ) { backStackEntry ->

            val args = backStackEntry.arguments!!

            val id = args.getInt("id")
            val title = args.getString("title") ?: ""
            val notes = args.getString("notes") ?: ""
            val category = args.getInt("category")
            val dateMillis = args.getLong("dateMillis")
            val timeMillis = args.getLong("timeMillis")
            val isCompleted = args.getBoolean("isCompleted")

            TaskDetail(
                navController = navController,
               id,
                 title,
                notes,
                 category,
                 dateMillis,
            timeMillis,
                 isCompleted
            )
        }



    }

}

sealed class Screen(val route: String, val title: String) {
    object SplashScreen : Screen("SplashScreen", "SplashScreen")
    object HomeScreen : Screen("HomeScreen", "HomeScreen")
    object DetailScreen : Screen("DetailScreen", "DetailScreen")
    object TaskDetail : Screen("TaskDetail", "TaskDetail")
}