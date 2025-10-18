package edu.gvsu.cis.bookwave.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import edu.gvsu.cis.bookwave.ui.screen.*
import edu.gvsu.cis.bookwave.viewmodel.BooksViewModel
import edu.gvsu.cis.bookwave.viewmodel.MessageViewModel

@Composable
fun Nav() {
    val navController = rememberNavController()
    val booksViewModel: BooksViewModel = viewModel()
    val messageViewModel: MessageViewModel = viewModel()  // NOUVEAU

    NavHost(navController = navController, startDestination = Routes.LOGIN_SCREEN) {

        composable(route = Routes.LOGIN_SCREEN) {
            LoginScreen(navController)
        }

        composable(route = Routes.HOME_SCREEN) {
            HomeScreen(navController, booksViewModel)
        }

        composable(route = Routes.ACCOUNT_SCREEN) {
            AccountScreen(navController)
        }

        composable(route = Routes.SEARCH_SCREEN) {
            SearchScreen(navController)
        }

        composable(route = Routes.FAVORITE_SCREEN) {
            FavoriteScreen(navController, booksViewModel)
        }

        composable(route = Routes.MESSAGE_SCREEN) {
            MessageScreen(navController, messageViewModel)  // MODIFIÉ
        }

        // NOUVEAU: Route pour le Chat Screen
        composable(
            route = "${Routes.CHAT_SCREEN}/{userId}",
            arguments = listOf(navArgument("userId") { type = NavType.IntType })
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getInt("userId") ?: 0
            ChatScreen(navController, userId, messageViewModel)
        }

        composable(
            route = "${Routes.BOOK_DETAILS_SCREEN}/{bookId}",
            arguments = listOf(navArgument("bookId") { type = NavType.IntType })
        ) { backStackEntry ->
            val bookId = backStackEntry.arguments?.getInt("bookId") ?: 0
            BookDetailsScreen(navController, bookId, booksViewModel)
        }

        composable(
            route = "${Routes.PLAYER_SCREEN}/{bookId}",
            arguments = listOf(navArgument("bookId") { type = NavType.IntType })
        ) { backStackEntry ->
            val bookId = backStackEntry.arguments?.getInt("bookId") ?: 0
            PlayerScreen(navController, bookId, booksViewModel)
        }

        composable(route = Routes.CHATBOT_SCREEN) {
            ChatbotScreen(navController)
        }

        composable(route = Routes.SIGNUP_SCREEN) {
            SignUpScreen(navController)
        }

    }
}
