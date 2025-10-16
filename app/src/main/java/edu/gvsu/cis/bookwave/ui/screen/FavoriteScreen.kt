package edu.gvsu.cis.bookwave.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import edu.gvsu.cis.bookwave.navigation.BottomNavigationBar
import edu.gvsu.cis.bookwave.navigation.Routes
import edu.gvsu.cis.bookwave.ui.components.BookCard
import edu.gvsu.cis.bookwave.viewmodel.BooksViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FavoriteBorder

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FavoriteScreen(
    navController: NavController,
    viewModel: BooksViewModel = viewModel()
) {
    val favoriteBooks by viewModel.favoriteBooks.collectAsState()

    Scaffold(
        topBar = {
            Column {
                CenterAlignedTopAppBar(
                    navigationIcon = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            IconButton(onClick = {navController.navigate(Routes.HOME_SCREEN)}) {
                                Icon(Icons.Default.ArrowBack, contentDescription = "")
                            }
                            // Ligne verticale après l'icône menu
                        }
                    },
                    title = {
                        Text(text = "Favorite")
                    },

                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = Color(0xFFF5E6D3) // Couleur beige comme dans l'image
                    )
                )
                // Ligne horizontale noire sous le TopAppBar
                Divider(
                    color = Color.Black,
                    thickness = 1.dp
                )
            }
        },
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    ) { paddingValues ->

        if (favoriteBooks.isEmpty()) {
            // Message quand aucun favori
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFF5E6D3))
                    .padding(paddingValues)
                    .padding(32.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Filled.FavoriteBorder,
                    contentDescription = "No favorites",
                    modifier = Modifier.size(80.dp),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "No Favorites Yet",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Start adding books to your favorites by tapping the heart icon on book details.",
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                    lineHeight = 24.sp
                )

                Spacer(modifier = Modifier.height(32.dp))


            }
        } else {
            // Grille de livres favoris
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFF5E6D3))
                    .padding(paddingValues),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(favoriteBooks) { book ->
                    BookCard(
                        book = book,
                        onClick = {
                            navController.navigate("${Routes.BOOK_DETAILS_SCREEN}/${book.id}")
                        }
                    )
                }
            }
        }
    }
}