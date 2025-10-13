package edu.gvsu.cis.bookwave.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

data class BottomNavItem(
    val route: String,
    val icon: ImageVector,
    val label: String
)

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        BottomNavItem(Routes.HOME_SCREEN, Icons.Default.Home, "Home"),
        BottomNavItem(Routes.SEARCH_SCREEN, Icons.Default.Search, "Search"),
        BottomNavItem(Routes.FAVORITE_SCREEN, Icons.Default.BookmarkBorder, "Favorite"),
        BottomNavItem(Routes.ACCOUNT_SCREEN, Icons.Default.AccountCircle, "Account")
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Column {
        // Ligne horizontale noire en haut
        Divider(
            color = Color.Black,
            thickness = 1.dp
        )

        NavigationBar(
            containerColor = Color(0xFFF5E6D3) // Couleur pour le background
        ) {
            items.forEach { item ->
                NavigationBarItem(
                    icon = { Icon(item.icon, contentDescription = item.label) },
                    label = { Text(item.label) },
                    selected = currentRoute == item.route,
                    onClick = {
                        navController.navigate(item.route) {
                            popUpTo(Routes.HOME_SCREEN) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFFFF5722), // Couleur orange pour l'icône sélectionnée
                        selectedTextColor = Color(0xFFFF5722), // Couleur orange pour le texte sélectionné
                        unselectedIconColor = Color.Black,
                        unselectedTextColor = Color.Black,
                        indicatorColor = Color.Transparent // Pour éviter l'indicateur par défaut
                    )
                )
            }
        }
    }
}