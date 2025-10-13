package edu.gvsu.cis.bookwave.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import edu.gvsu.cis.bookwave.ui.screen.*

@Composable
fun Nav() {
    val navController = rememberNavController()



    NavHost(navController = navController, startDestination = Routes.HOME_SCREEN) {

        composable(route = Routes.LOGIN_SCREEN) {
            LoginScreen(navController)
        }

        composable(route = Routes.HOME_SCREEN) {
            HomeScreen(navController)
        }

        composable(route = Routes.SEARCH_SCREEN) {
            SearchScreen(navController)
        }

        composable(route = Routes.FAVORITE_SCREEN) {
            FavoriteScreen(navController)
        }

        composable(route = Routes.ACCOUNT_SCREEN) {
            AccountScreen(navController)
        }


    }
}
