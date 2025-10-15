package edu.gvsu.cis.bookwave.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.navigation.NavController
import edu.gvsu.cis.bookwave.R
import androidx.compose.material3.Divider
import edu.gvsu.cis.bookwave.navigation.BottomNavigationBar
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Divider
import edu.gvsu.cis.bookwave.data.BooksData
import edu.gvsu.cis.bookwave.navigation.BottomNavigationBar
import edu.gvsu.cis.bookwave.navigation.Routes
import edu.gvsu.cis.bookwave.ui.components.BooksLazyRow

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(navController: NavController){

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
                            Divider(
                                color = Color.Black,
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .width(1.dp)
                            )
                        }
                    },
                    title = {
                        Text(text = "BookWave")
                    },
                    actions = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Divider(
                                color = Color.Black,
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .width(1.dp)
                            )
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

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            item {
                Spacer(modifier = Modifier.height(16.dp))

                // Section Title
                Text(
                    text = "Popular Audiobooks",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 20.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))
            }

            item {
                // Books LazyRow
                BooksLazyRow(
                    books = BooksData.myBooks,
                    onBookClick = { book ->
                        navController.navigate("${Routes.BOOK_DETAILS_SCREEN}/${book.id}")
                    }
                )
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))

                // Section Title
                Text(
                    text = "Recently Added",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 20.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))
            }

            item {
                // Another LazyRow (same books for demo)
                BooksLazyRow(
                    books = BooksData.myBooks.reversed(),
                    onBookClick = { book ->
                        navController.navigate("${Routes.BOOK_DETAILS_SCREEN}/${book.id}")
                    }
                )
            }
        }
    }
}



