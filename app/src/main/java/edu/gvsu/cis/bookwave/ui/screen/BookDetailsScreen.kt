package edu.gvsu.cis.bookwave.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import edu.gvsu.cis.bookwave.viewmodel.BooksViewModel
import edu.gvsu.cis.bookwave.navigation.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookDetailsScreen(
    navController: NavController,
    bookId: Int,
    viewModel: BooksViewModel = viewModel()
) {
    val books by viewModel.books.collectAsState()
    val book = books.find { it.id == bookId }

    if (book == null) {
        LaunchedEffect(Unit) {
            navController.popBackStack()
        }
        return
    }

    val isFavorite = book.isFavorite

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5E6D3))
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            // Hero Section avec Image et Play Button
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(500.dp)
                ) {
                    // Image de fond
                    Image(
                        painter = painterResource(id = book.coverImageRes),
                        contentDescription = book.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )

                    // Overlay gradient
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Black.copy(alpha = 0.3f),
                                        Color.Black.copy(alpha = 0.5f),
                                        Color(0xFFF5E6D3)
                                    ),
                                    startY = 400f
                                )
                            )
                    )

                    // Top Bar Icons
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .statusBarsPadding()
                            .padding(horizontal = 8.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        IconButton(
                            onClick = { navController.popBackStack() },
                            modifier = Modifier
                                .shadow(4.dp, CircleShape)
                                .background(Color.White.copy(alpha = 0.9f), CircleShape)
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = Color.Black
                            )
                        }

                        IconButton(
                            onClick = { viewModel.toggleFavorite(bookId) },
                            modifier = Modifier
                                .shadow(4.dp, CircleShape)
                                .background(Color.White.copy(alpha = 0.9f), CircleShape)
                        ) {
                            Icon(
                                imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                                contentDescription = "Favorite",
                                tint = if (isFavorite) Color(0xFFE67E50) else Color.Black
                            )
                        }
                    }

                    // Play Button centré
                    Surface(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(72.dp),
                        shape = CircleShape,
                        color = Color.Black,
                        shadowElevation = 12.dp
                    ) {
                        IconButton(
                            onClick = { navController.navigate("${Routes.PLAYER_SCREEN}/${bookId}") },
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Icon(
                                imageVector = Icons.Filled.PlayArrow,
                                contentDescription = "Play",
                                tint = Color.White,
                                modifier = Modifier.size(36.dp)
                            )
                        }
                    }
                }
            }

            // Content Section
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFF5E6D3))
                        .padding(24.dp)
                ) {
                    // Title
                    Text(
                        text = book.title,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        fontFamily = FontFamily.Monospace,
                        letterSpacing = (-0.5).sp
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Author
                    Text(
                        text = "by ${book.author}",
                        fontSize = 18.sp,
                        color = Color.Black.copy(alpha = 0.7f),
                        fontFamily = FontFamily.Monospace
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Stats Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        StatCard(
                            icon = Icons.Filled.Star,
                            value = book.rating.toString(),
                            label = "Rating",
                            iconTint = Color(0xFFE67E50)
                        )
                        StatCard(
                            icon = Icons.Filled.DateRange,
                            value = book.publishedYear.toString(),
                            label = "Year",
                            iconTint = Color.Black
                        )
                        StatCard(
                            icon = Icons.Filled.AccessTime,
                            value = "${book.duration}m",
                            label = "Duration",
                            iconTint = Color.Black
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Category Badge
                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = Color.Black
                    ) {
                        Text(
                            text = book.category,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            fontFamily = FontFamily.Monospace,
                            modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(28.dp))

                    // Description
                    Text(
                        text = "About this book",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        fontFamily = FontFamily.Monospace,
                        letterSpacing = (-0.5).sp
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = book.description,
                        fontSize = 16.sp,
                        lineHeight = 26.sp,
                        color = Color.Black.copy(alpha = 0.8f),
                        fontFamily = FontFamily.Monospace
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    // Action Buttons
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Button(
                            onClick = { navController.navigate("${Routes.PLAYER_SCREEN}/${bookId}") },
                            modifier = Modifier
                                .weight(1f)
                                .height(56.dp),
                            shape = RoundedCornerShape(16.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Black
                            )
                        ) {
                            Icon(
                                imageVector = Icons.Filled.PlayArrow,
                                contentDescription = null,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                "Listen Now",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Monospace
                            )
                        }

                    }

                    Spacer(modifier = Modifier.height(32.dp))
                }
            }
        }
    }
}

@Composable
fun StatCard(
    icon: ImageVector,
    value: String,
    label: String,
    iconTint: Color
) {
    Surface(
        modifier = Modifier
            .width(100.dp)
            .height(100.dp),
        shape = RoundedCornerShape(8.dp),
        color = Color.White,
        shadowElevation = 4.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = iconTint,
                modifier = Modifier.size(28.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = value,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                fontFamily = FontFamily.Monospace
            )
            Text(
                text = label,
                fontSize = 11.sp,
                color = Color.Black.copy(alpha = 0.6f),
                fontFamily = FontFamily.Monospace
            )
        }
    }
}