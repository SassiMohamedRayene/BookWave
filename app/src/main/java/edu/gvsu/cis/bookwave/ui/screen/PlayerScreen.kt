package edu.gvsu.cis.bookwave.ui.screen

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import edu.gvsu.cis.bookwave.viewmodel.AudioPlayerViewModel
import edu.gvsu.cis.bookwave.viewmodel.BooksViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerScreen(
    navController: NavController,
    bookId: Int,
    booksViewModel: BooksViewModel = viewModel(),
    audioViewModel: AudioPlayerViewModel = viewModel()
) {
    val books by booksViewModel.books.collectAsState()
    val book = books.find { it.id == bookId }

    val isPlaying by audioViewModel.isPlaying.collectAsState()
    val currentPosition by audioViewModel.currentPosition.collectAsState()
    val duration by audioViewModel.duration.collectAsState()
    val playbackSpeed by audioViewModel.playbackSpeed.collectAsState()

    LaunchedEffect(book) {
        book?.let {
            audioViewModel.initializePlayer(it.audioUrl)
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            audioViewModel.releasePlayer()
        }
    }

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
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFE8D5C4),
                        Color(0xFFF5E6D3),
                        Color(0xFFFFF8F0)
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
        ) {
            // Top Bar - Style Spotify
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.Black,
                        modifier = Modifier.size(28.dp)
                    )
                }

                Row {
                    IconButton(onClick = {
                        booksViewModel.toggleFavorite(bookId)
                    }) {
                        Icon(
                            imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                            contentDescription = "Favorite",
                            tint = if (isFavorite) Color(0xFFE67E50) else Color.Black,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Book Cover - Style Spotify (carré avec coins arrondis)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                contentAlignment = Alignment.Center
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f),
                    shape = RoundedCornerShape(8.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Image(
                        painter = painterResource(id = book.coverImageRes),
                        contentDescription = book.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Book Info
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
            ) {
                Text(
                    text = book.title,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    fontFamily = FontFamily.Monospace,
                    letterSpacing = (-0.5).sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = book.author,
                    fontSize = 16.sp,
                    color = Color.Black.copy(alpha = 0.7f),
                    fontFamily = FontFamily.Monospace
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            // Progress Bar
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
            ) {
                Slider(
                    value = currentPosition.toFloat(),
                    onValueChange = { audioViewModel.seekTo(it.toLong()) },
                    valueRange = 0f..duration.toFloat().coerceAtLeast(1f),
                    colors = SliderDefaults.colors(
                        thumbColor = Color.Black,
                        activeTrackColor = Color.Black,
                        inactiveTrackColor = Color.Black.copy(alpha = 0.2f)
                    ),
                    modifier = Modifier.height(4.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = formatTime(currentPosition),
                        fontSize = 12.sp,
                        color = Color.Black.copy(alpha = 0.6f),
                        fontFamily = FontFamily.Monospace
                    )
                    Text(
                        text = formatTime(duration),
                        fontSize = 12.sp,
                        color = Color.Black.copy(alpha = 0.6f),
                        fontFamily = FontFamily.Monospace
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Playback Controls - Style Spotify
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Speed Control
                Surface(
                    modifier = Modifier.size(40.dp),
                    shape = CircleShape,
                    color = Color.Black.copy(alpha = 0.1f)
                ) {
                    IconButton(
                        onClick = { audioViewModel.cyclePlaybackSpeed() }
                    ) {
                        Text(
                            text = "${playbackSpeed}x",
                            color = Color.Black,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                }

                // Skip Previous
                IconButton(
                    onClick = { audioViewModel.skipBackward() },
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Replay10,
                        contentDescription = "Skip -10s",
                        tint = Color.Black,
                        modifier = Modifier.size(36.dp)
                    )
                }

                // Play/Pause Button - Grand bouton rond Spotify
                Surface(
                    modifier = Modifier.size(64.dp),
                    shape = CircleShape,
                    color = Color.Black
                ) {
                    IconButton(
                        onClick = {
                            if (isPlaying) {
                                audioViewModel.pause()
                            } else {
                                audioViewModel.play()
                            }
                        },
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Icon(
                            imageVector = if (isPlaying) Icons.Filled.Pause else Icons.Filled.PlayArrow,
                            contentDescription = if (isPlaying) "Pause" else "Play",
                            tint = Color.White,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }

                // Skip Forward
                IconButton(
                    onClick = { audioViewModel.skipForward() },
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Forward10,
                        contentDescription = "Skip +10s",
                        tint = Color.Black,
                        modifier = Modifier.size(36.dp)
                    )
                }

                // Timer
                Surface(
                    modifier = Modifier.size(40.dp),
                    shape = CircleShape,
                    color = Color.Black.copy(alpha = 0.1f)
                ) {
                    IconButton(onClick = { /* Sleep timer */ }) {
                        Icon(
                            imageVector = Icons.Filled.Timer,
                            contentDescription = "Timer",
                            tint = Color.Black,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

private fun formatTime(milliseconds: Long): String {
    val totalSeconds = milliseconds / 1000
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    return "%d:%02d".format(minutes, seconds)
}