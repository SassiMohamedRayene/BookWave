package edu.gvsu.cis.bookwave.ui.screen

import android.annotation.SuppressLint
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
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
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: BooksViewModel = viewModel()
) {
    val books by viewModel.books.collectAsState()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            NavigationDrawerContent(
                navController = navController,
                onCloseDrawer = {
                    scope.launch {
                        drawerState.close()
                    }
                }
            )
        },
        gesturesEnabled = true
    ) {
        Scaffold(
            topBar = {
                Column {
                    CenterAlignedTopAppBar(
                        navigationIcon = {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                IconButton(onClick = {
                                    scope.launch {
                                        drawerState.open()
                                    }
                                }) {
                                    Icon(
                                        Icons.Default.Menu,
                                        contentDescription = "Menu",
                                        tint = Color.Black
                                    )
                                }
                            }
                        },
                        title = {
                            Text(
                                text = "BookWave",
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
                                        .clickable { navController.navigate(Routes.ACCOUNT_SCREEN) }
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
                val infiniteTransition = rememberInfiniteTransition(label = "pulse")
                val scale by infiniteTransition.animateFloat(
                    initialValue = 1f,
                    targetValue = 1.08f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(1200, easing = FastOutSlowInEasing),
                        repeatMode = RepeatMode.Reverse
                    ),
                    label = "pulse"
                )

                FloatingActionButton(
                    onClick = { navController.navigate(Routes.CHATBOT_SCREEN) },
                    containerColor = Color(0xFFE67E50),
                    contentColor = Color.White,
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                        .size(68.dp),
                    shape = RoundedCornerShape(20.dp),
                    elevation = FloatingActionButtonDefaults.elevation(
                        defaultElevation = 12.dp,
                        pressedElevation = 16.dp
                    )
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .scale(scale)
                                .background(
                                    Color.White.copy(alpha = 0.25f),
                                    shape = CircleShape
                                )
                        )

                        Icon(
                            imageVector = Icons.Default.SmartToy,
                            contentDescription = "AI Chatbot",
                            modifier = Modifier.size(32.dp),
                            tint = Color.White
                        )
                    }
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
                    Spacer(modifier = Modifier.height(12.dp))

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
                    Spacer(modifier = Modifier.height(18.dp))

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
}

@Composable
fun NavigationDrawerContent(
    navController: NavController,
    onCloseDrawer: () -> Unit
) {
    ModalDrawerSheet(
        drawerContainerColor = Color(0xFFF5E6D3),
        modifier = Modifier.width(300.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 24.dp)
        ) {
            // En-tête du drawer
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.eminem),
                    contentDescription = "Profile",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "BookWave",
                    style = TextStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        fontFamily = FontFamily.Monospace,
                        letterSpacing = (-0.5).sp
                    )
                )

                Text(
                    text = "Your Audio Library",
                    style = TextStyle(
                        fontSize = 14.sp,
                        color = Color.Black.copy(alpha = 0.6f),
                        fontFamily = FontFamily.Monospace
                    )
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 16.dp),
                color = Color.Black.copy(alpha = 0.2f),
                thickness = 1.dp
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Items de navigation
            DrawerNavigationItem(
                icon = Icons.Default.Home,
                label = "Home",
                onClick = {
                    navController.navigate(Routes.HOME_SCREEN)
                    onCloseDrawer()
                }
            )

            DrawerNavigationItem(
                icon = Icons.Default.AccountCircle,
                label = "Account",
                onClick = {
                    navController.navigate(Routes.ACCOUNT_SCREEN)
                    onCloseDrawer()
                }
            )

            DrawerNavigationItem(
                icon = Icons.Default.Settings,
                label = "Settings",
                onClick = {

                    onCloseDrawer()
                }
            )

            Spacer(modifier = Modifier.weight(1f))

            // Footer
            DrawerNavigationItem(
                icon = Icons.Default.ExitToApp,
                label = "Logout",
                onClick = {
                    navController.navigate(Routes.LOGIN_SCREEN) {
                        popUpTo(0) { inclusive = true }
                    }
                    onCloseDrawer()
                },
                isDestructive = true
            )
        }
    }
}

@Composable
fun DrawerNavigationItem(
    icon: ImageVector,
    label: String,
    onClick: () -> Unit,
    isDestructive: Boolean = false
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = if (isDestructive) Color(0xFFE67E50) else Color.Black,
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.width(20.dp))

        Text(
            text = label,
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = if (isDestructive) Color(0xFFE67E50) else Color.Black,
                fontFamily = FontFamily.Monospace
            )
        )
    }
}