package edu.gvsu.cis.bookwave.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import edu.gvsu.cis.bookwave.R
import edu.gvsu.cis.bookwave.navigation.BottomNavigationBar
import edu.gvsu.cis.bookwave.navigation.Routes
import edu.gvsu.cis.bookwave.ui.components.BooksLazyRow
import edu.gvsu.cis.bookwave.viewmodel.BooksViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: BooksViewModel = viewModel()
) {
    val books by viewModel.books.collectAsState()

    Scaffold(
        topBar = {
            Column {
                CenterAlignedTopAppBar(
                    navigationIcon = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            IconButton(onClick = {}) {
                                Icon(Icons.Default.Menu, contentDescription = "")
                            }
                        }
                    },
                    title = {
                        Text(text = "BookWave",
                            style = TextStyle(
                                fontSize = 28.sp,
                                color = Color.Black,
                                fontFamily = FontFamily.Monospace,
                                letterSpacing = (-0.5).sp
                            ),
                        )
                    },
                    actions = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.eminem),
                                contentDescription = "Profile",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .padding(horizontal = 16.dp)
                                    .size(40.dp)
                                    .clip(CircleShape)
                                    .clickable { /* navigation vers profil */ }
                            )
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = Color(0xFFF5E6D3)
                    )
                )
                HorizontalDivider(
                    color = Color.Black,
                    thickness = 1.dp
                )
            }
        },
        bottomBar = {
            BottomNavigationBar(navController = navController)
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(Routes.CHATBOT_SCREEN) },
                containerColor = Color(0xFF6B4226),
                contentColor = Color.White,
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.SmartToy,
                    contentDescription = "AI Chatbot"
                )
            }
        }
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFFF5E6D3))
        ) {
            item {
                Spacer(modifier = Modifier.height(28.dp))

                Text(
                    text = "Popular Audiobooks",
                    fontSize = 22.sp,
                    style = TextStyle(
                        fontSize = 28.sp,
                        color = Color.Black,
                        fontFamily = FontFamily.Monospace,
                        letterSpacing = (-0.5).sp
                    ),
                    modifier = Modifier.padding(horizontal = 20.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))
            }

            item {
                BooksLazyRow(
                    books = books,
                    onBookClick = { book ->
                        navController.navigate("${Routes.BOOK_DETAILS_SCREEN}/${book.id}")
                    }
                )
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Recently Added",
                    fontSize = 22.sp,
                    style = TextStyle(
                        fontSize = 28.sp,
                        color = Color.Black,
                        fontFamily = FontFamily.Monospace,
                        letterSpacing = (-0.5).sp
                    ),
                    modifier = Modifier.padding(horizontal = 20.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))
            }

            item {
                BooksLazyRow(
                    books = books.reversed(),
                    onBookClick = { book ->
                        navController.navigate("${Routes.BOOK_DETAILS_SCREEN}/${book.id}")
                    }
                )
            }
        }
    }
}