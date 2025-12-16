package com.example.todomobileapp.Screen

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.HomeScreen.route) {
        composable(Screen.HomeScreen.route) { HomeScreen(navController) }
        composable(Screen.DetailScreen.route) { DetailScreen(navController) }
        composable(
            route = Screen.TaskDetail.route +
                    "/{title}/{notes}/{category}/{dateMillis}/{timeMillis}/{isCompleted}",
            arguments = listOf(
                navArgument("title") { type = NavType.StringType },
                navArgument("notes") {
                    type = NavType.StringType
                    defaultValue = ""
                },
                navArgument("category") { type = NavType.IntType },
                navArgument("dateMillis") { type = NavType.LongType },
                navArgument("timeMillis") { type = NavType.LongType },
                navArgument("isCompleted") { type = NavType.BoolType }
            )
        ) { backStackEntry ->

            val args = backStackEntry.arguments!!

            val title = args.getString("title") ?: ""
            val notes = args.getString("notes") ?: ""
            val category = args.getInt("category")
            val dateMillis = args.getLong("dateMillis")
            val timeMillis = args.getLong("timeMillis")
            val isCompleted = args.getBoolean("isCompleted")

            TaskDetail(
                navController = navController,
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
    object HomeScreen : Screen("HomeScreen", "HomeScreen")
    object DetailScreen : Screen("DetailScreen", "DetailScreen")
    object TaskDetail : Screen("TaskDetail", "TaskDetail")
}