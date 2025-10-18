package edu.gvsu.cis.bookwave.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import edu.gvsu.cis.bookwave.navigation.Routes
import edu.gvsu.cis.bookwave.viewmodel.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AccountScreen(
    navController: NavController,
    userViewModel: UserViewModel = viewModel()
) {
    // Pour les fake data, créer un utilisateur si aucun n'existe
    if (userViewModel.currentUser == null) {
        userViewModel.registerUser(
            id = 1,
            username = "Eminem",
            email = "eminem@bookwave.com",
            password = "password123",
            profileImageUrl = null
        )
    }

    val user = userViewModel.currentUser

    Scaffold(
        topBar = {
            Column {
                CenterAlignedTopAppBar(
                    navigationIcon = {
                        IconButton(onClick = { navController.navigateUp() }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Retour"
                            )
                        }
                    },
                    title = {
                        Text(
                            text = "Mon Profil",
                            style = TextStyle(
                                fontSize = 28.sp,
                                color = Color.Black,
                                fontFamily = FontFamily.Monospace,
                                letterSpacing = (-0.5).sp
                            )
                        )
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
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFFF5E6D3)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Spacer(modifier = Modifier.height(32.dp))

                // Photo de profil avec bordure
                Box(
                    modifier = Modifier
                        .size(140.dp)
                        .background(Color.White, CircleShape)
                        .padding(4.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.eminem),
                        contentDescription = "Photo de profil",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Nom d'utilisateur
                Text(
                    text = user?.username ?: "Utilisateur",
                    style = TextStyle(
                        fontSize = 28.sp,
                        color = Color.Black,
                        fontFamily = FontFamily.Monospace,
                        letterSpacing = (-0.5).sp,
                        fontWeight = FontWeight.Bold
                    )
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = user?.email ?: "email@bookwave.com",
                    fontSize = 14.sp,
                    color = Color.Black.copy(alpha = 0.6f),
                    fontFamily = FontFamily.Monospace
                )

                Spacer(modifier = Modifier.height(32.dp))
            }

            item {
                // Statistiques
                Text(
                    text = "Statistiques",
                    style = TextStyle(
                        fontSize = 22.sp,
                        color = Color.Black,
                        fontFamily = FontFamily.Monospace,
                        letterSpacing = (-0.5).sp,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    StatCard(
                        title = "Livres écoutés",
                        value = "24",
                        icon = Icons.Default.Headphones,
                        modifier = Modifier.weight(1f)
                    )
                    StatCard(
                        title = "Temps d'écoute",
                        value = "156h",
                        icon = Icons.Default.Schedule,
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    StatCard(
                        title = "Favoris",
                        value = "12",
                        icon = Icons.Default.Favorite,
                        modifier = Modifier.weight(1f)
                    )
                    StatCard(
                        title = "Abonnement",
                        value = "Premium",
                        icon = Icons.Default.Star,
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))
            }

            item {
                // Paramètres
                Text(
                    text = "Paramètres",
                    style = TextStyle(
                        fontSize = 22.sp,
                        color = Color.Black,
                        fontFamily = FontFamily.Monospace,
                        letterSpacing = (-0.5).sp,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))
            }

            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                ) {
                    SettingItem(
                        icon = Icons.Default.Person,
                        title = "Modifier le profil",
                        onClick = { /* TODO */ }
                    )

                    SettingItem(
                        icon = Icons.Default.Notifications,
                        title = "Notifications",
                        onClick = { /* TODO */ }
                    )

                    SettingItem(
                        icon = Icons.Default.Settings,
                        title = "Préférences",
                        onClick = { /* TODO */ }
                    )

                    SettingItem(
                        icon = Icons.Default.Help,
                        title = "Aide & Support",
                        onClick = { /* TODO */ }
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))
            }

            item {
                // Bouton de déconnexion
                Button(
                    onClick = {
                        userViewModel.logoutUser()
                        navController.navigate(Routes.LOGIN_SCREEN) {
                            popUpTo(0) { inclusive = true }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFE67E50)
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Logout,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Déconnexion",
                        fontSize = 16.sp,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Composable
fun StatCard(
    title: String,
    value: String,
    icon: ImageVector,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.height(100.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = Color(0xFFE67E50),
                modifier = Modifier.size(24.dp)
            )

            Column {
                Text(
                    text = value,
                    style = TextStyle(
                        fontSize = 24.sp,
                        color = Color.Black,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    text = title,
                    fontSize = 12.sp,
                    color = Color.Black.copy(alpha = 0.6f),
                    fontFamily = FontFamily.Monospace
                )
            }
        }
    }
}

@Composable
fun SettingItem(
    icon: ImageVector,
    title: String,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 1.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = Color(0xFFE67E50),
                modifier = Modifier.size(24.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = title,
                fontSize = 16.sp,
                color = Color.Black,
                fontFamily = FontFamily.Monospace,
                modifier = Modifier.weight(1f)
            )

            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = "Aller",
                tint = Color.Black.copy(alpha = 0.4f),
                modifier = Modifier.size(20.dp)
            )
        }
    }
}