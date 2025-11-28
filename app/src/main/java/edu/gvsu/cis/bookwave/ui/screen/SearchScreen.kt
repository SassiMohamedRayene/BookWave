package edu.gvsu.cis.bookwave.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
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

// Catégories avec leurs icônes
data class Category(
    val name: String,
    val icon: ImageVector
)

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SearchScreen(
    navController: NavController,
    viewModel: BooksViewModel = viewModel()
) {
    val books by viewModel.books.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("All") }

    // Liste des catégories
    val categories = listOf(
        Category("All", Icons.Filled.Menu),
        Category("Romance", Icons.Filled.Favorite),
        Category("Classic", Icons.Filled.MenuBook),
        Category("Dystopian", Icons.Filled.Warning),
        Category("Career & Growth", Icons.Filled.TrendingUp),
        Category("Business", Icons.Filled.Business),
        Category("Finance & Money Management", Icons.Filled.AccountBalance),
        Category("Contemporary Fiction", Icons.Filled.Book),
        Category("Politics", Icons.Filled.Gavel),
        Category("Mystery & Crime Fiction", Icons.Filled.Search),
        Category("Sport & Recreation", Icons.Filled.SportsBaseball),
        Category("Social Science", Icons.Filled.School),
        Category("War", Icons.Filled.LocalFireDepartment),
    )

    // Filtrer les livres
    val filteredBooks = books.filter { book ->
        val matchesSearch = book.title.contains(searchQuery, ignoreCase = true) ||
                book.author.contains(searchQuery, ignoreCase = true)
        val matchesCategory = selectedCategory == "All" || book.category == selectedCategory
        matchesSearch && matchesCategory
    }

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier.background(Color(0xFFFDD835))
            ) {
                // Header
                Text(
                    text = "",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp),
                    color = Color.Black
                )

                // Search Bar
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 8.dp),
                    placeholder = {
                        Text(
                            text = "Title, author, host, or topic",
                            color = Color.Gray
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "Search",
                            tint = Color.Black
                        )
                    },
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(onClick = { searchQuery = "" }) {
                                Icon(
                                    imageVector = Icons.Filled.Close,
                                    contentDescription = "Clear",
                                    tint = Color.Black
                                )
                            }
                        }
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFFFFF9E6),
                        unfocusedContainerColor = Color(0xFFFFF9E6),
                        focusedBorderColor = Color.Black,
                        unfocusedBorderColor = Color.Black
                    ),
                    shape = RoundedCornerShape(8.dp),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(16.dp))
            }
        },
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFFF5E6D3))
        ) {
            // Categories ScrollableTabRow
            ScrollableTabRow(
                selectedTabIndex = categories.indexOfFirst { it.name == selectedCategory },
                modifier = Modifier.fillMaxWidth(),
                containerColor = Color(0xFFF5E6D3),
                edgePadding = 0.dp,
                indicator = {},
                divider = {}
            ) {
                categories.forEach { category ->
                    CategoryTab(
                        category = category,
                        isSelected = selectedCategory == category.name,
                        onClick = { selectedCategory = category.name }
                    )
                }
            }



            // Results
            if (filteredBooks.isEmpty()) {
                // No results
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Filled.SearchOff,
                        contentDescription = "No results",
                        modifier = Modifier.size(80.dp),
                        tint = Color.Gray.copy(alpha = 0.5f)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "No books found",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Try adjusting your search or category filter",
                        fontSize = 14.sp,
                        color = Color.Gray,
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                // Books Grid
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(filteredBooks) { book ->
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
}

@Composable
fun CategoryTab(
    category: Category,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .padding(horizontal = 4.dp, vertical = 8.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(8.dp),
        color = if (isSelected) Color(0xFFE67E50) else Color.Transparent,
        border = if (!isSelected) {
            androidx.compose.foundation.BorderStroke(1.dp, Color.Black)
        } else null
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = category.icon,
                contentDescription = category.name,
                modifier = Modifier.size(18.dp),
                tint = if (isSelected) Color.Black else Color.Black
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = category.name,
                fontSize = 14.sp,
                color = if (isSelected) Color.Black else Color.Black,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
            )
        }
    }
}