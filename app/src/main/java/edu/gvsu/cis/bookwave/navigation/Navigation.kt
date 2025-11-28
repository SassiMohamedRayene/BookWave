package edu.gvsu.cis.bookwave.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import edu.gvsu.cis.bookwave.ui.screen.*
import edu.gvsu.cis.bookwave.viewmodel.AuthViewModel
import edu.gvsu.cis.bookwave.viewmodel.BooksViewModel
import edu.gvsu.cis.bookwave.viewmodel.FirebaseMessageViewModel

@Composable
fun NavWithFirebase() {
    val navController = rememberNavController()
    val authViewModel: AuthViewModel = viewModel()
    val booksViewModel: BooksViewModel = viewModel()
    val firebaseMessageViewModel: FirebaseMessageViewModel = viewModel()

    // Determine start destination based on auth state
    val startDestination = if (authViewModel.currentUser != null) {
        Routes.HOME_SCREEN
    } else {
        Routes.LOGIN_SCREEN
    }

    NavHost(navController = navController, startDestination = startDestination) {

        composable(route = Routes.LOGIN_SCREEN) {
            LoginScreen(navController, authViewModel)
        }

        composable(route = Routes.SIGNUP_SCREEN) {
            SignUpScreen(navController, authViewModel)
        }

        composable(route = Routes.HOME_SCREEN) {
            HomeScreen(navController, authViewModel, booksViewModel)
        }

        composable(route = Routes.ACCOUNT_SCREEN) {
            AccountScreen(navController, authViewModel)
        }

        composable(route = Routes.SEARCH_SCREEN) {
            SearchScreen(navController)
        }

        composable(route = Routes.FAVORITE_SCREEN) {
            FavoriteScreen(navController, booksViewModel)
        }

        // Version Firebase de MessageScreen
        composable(route = Routes.MESSAGE_SCREEN) {
            FirebaseMessageScreen(navController, firebaseMessageViewModel)
        }

        // Version Firebase de ChatScreen avec String userId au lieu de Int
        composable(
            route = "${Routes.CHAT_SCREEN}/{userId}",
            arguments = listOf(navArgument("userId") { type = NavType.StringType })
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: ""
            FirebaseChatScreen(navController, userId, firebaseMessageViewModel)
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

        composable(route = Routes.EDIT_PROFILE_SCREEN) {
            EditProfileScreen(navController, authViewModel)
        }
    }
}