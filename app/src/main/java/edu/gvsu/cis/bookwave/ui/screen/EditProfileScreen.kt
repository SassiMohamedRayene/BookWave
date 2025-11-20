package edu.gvsu.cis.bookwave.ui.screen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import edu.gvsu.cis.bookwave.R
import edu.gvsu.cis.bookwave.viewmodel.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    navController: NavController,
    authViewModel: AuthViewModel
) {
    val user = authViewModel.currentUser

    var username by remember { mutableStateOf(user?.displayName ?: "") }
    var currentPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var showCurrentPassword by remember { mutableStateOf(false) }
    var showNewPassword by remember { mutableStateOf(false) }
    var showConfirmPassword by remember { mutableStateOf(false) }

    var showPasswordFields by remember { mutableStateOf(false) }
    var message by remember { mutableStateOf<String?>(null) }
    var isError by remember { mutableStateOf(false) }

    val backgroundColor = Color(0xFFF5E6D3)
    val buttonColor = Color(0xFFE67E50)
    val textColor = Color(0xFF2D2D2D)

    // Image picker
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            authViewModel.uploadProfilePicture(it) { success, msg ->
                message = msg
                isError = !success
            }
        }
    }

    // Show message dialog
    message?.let { msg ->
        AlertDialog(
            onDismissRequest = {
                message = null
                if (!isError) {
                    navController.navigateUp()
                }
            },
            title = { Text(if (isError) "Error" else "Success") },
            text = { Text(msg) },
            confirmButton = {
                TextButton(onClick = {
                    message = null
                    if (!isError) {
                        navController.navigateUp()
                    }
                }) {
                    Text("OK")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            Column {
                CenterAlignedTopAppBar(
                    navigationIcon = {
                        IconButton(onClick = { navController.navigateUp() }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    },
                    title = {
                        Text(
                            text = "Edit Profile",
                            style = TextStyle(
                                fontSize = 24.sp,
                                color = textColor,
                                fontFamily = FontFamily.Monospace,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = backgroundColor
                    )
                )
                HorizontalDivider(color = textColor, thickness = 1.dp)
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(backgroundColor)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            // Profile Picture
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clickable { imagePickerLauncher.launch("image/*") }
            ) {
                if (authViewModel.profileImageUrl != null) {
                    AsyncImage(
                        model = authViewModel.profileImageUrl,
                        contentDescription = "Profile picture",
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape)
                            .border(4.dp, Color.White, CircleShape),
                        contentScale = ContentScale.Crop,
                        placeholder = painterResource(id = R.drawable.eminem),
                        error = painterResource(id = R.drawable.eminem)
                    )
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.eminem),
                        contentDescription = "Default profile picture",
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape)
                            .border(4.dp, Color.White, CircleShape),
                        contentScale = ContentScale.Crop
                    )
                }

                // Camera icon overlay
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                        .background(Color.Black.copy(alpha = 0.3f)),
                    contentAlignment = Alignment.Center
                ) {
                    if (authViewModel.isLoading) {
                        CircularProgressIndicator(
                            color = Color.White,
                            modifier = Modifier.size(30.dp)
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.CameraAlt,
                            contentDescription = "Change photo",
                            tint = Color.White,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                }
            }

            Text(
                text = "Tap to change photo",
                fontSize = 12.sp,
                color = textColor.copy(alpha = 0.6f),
                fontFamily = FontFamily.Monospace,
                modifier = Modifier.padding(top = 8.dp)
            )

            Spacer(modifier = Modifier.height(30.dp))

            // Username
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Username", fontFamily = FontFamily.Monospace) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = buttonColor,
                    focusedLabelColor = buttonColor
                ),
                textStyle = TextStyle(fontFamily = FontFamily.Monospace)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Email (read-only)
            OutlinedTextField(
                value = user?.email ?: "",
                onValueChange = {},
                label = { Text("Email", fontFamily = FontFamily.Monospace) },
                modifier = Modifier.fillMaxWidth(),
                enabled = false,
                shape = RoundedCornerShape(12.dp),
                textStyle = TextStyle(fontFamily = FontFamily.Monospace)
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Change Password Button
            TextButton(
                onClick = { showPasswordFields = !showPasswordFields }
            ) {
                Text(
                    text = if (showPasswordFields) "Cancel Password Change" else "Change Password",
                    color = buttonColor,
                    fontFamily = FontFamily.Monospace
                )
            }

            // Password Fields (show/hide)
            if (showPasswordFields) {
                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = currentPassword,
                    onValueChange = { currentPassword = it },
                    label = { Text("Current Password", fontFamily = FontFamily.Monospace) },
                    visualTransformation = if (showCurrentPassword)
                        VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = { showCurrentPassword = !showCurrentPassword }) {
                            Icon(
                                imageVector = if (showCurrentPassword)
                                    Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                contentDescription = "Toggle password"
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = buttonColor,
                        focusedLabelColor = buttonColor
                    ),
                    textStyle = TextStyle(fontFamily = FontFamily.Monospace)
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = newPassword,
                    onValueChange = { newPassword = it },
                    label = { Text("New Password", fontFamily = FontFamily.Monospace) },
                    visualTransformation = if (showNewPassword)
                        VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = { showNewPassword = !showNewPassword }) {
                            Icon(
                                imageVector = if (showNewPassword)
                                    Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                contentDescription = "Toggle password"
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = buttonColor,
                        focusedLabelColor = buttonColor
                    ),
                    textStyle = TextStyle(fontFamily = FontFamily.Monospace)
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    label = { Text("Confirm New Password", fontFamily = FontFamily.Monospace) },
                    visualTransformation = if (showConfirmPassword)
                        VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = { showConfirmPassword = !showConfirmPassword }) {
                            Icon(
                                imageVector = if (showConfirmPassword)
                                    Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                contentDescription = "Toggle password"
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = buttonColor,
                        focusedLabelColor = buttonColor
                    ),
                    textStyle = TextStyle(fontFamily = FontFamily.Monospace)
                )
            }

            Spacer(modifier = Modifier.height(30.dp))

            // Save Button
            Button(
                onClick = {
                    // Validate
                    if (showPasswordFields) {
                        if (currentPassword.isBlank() || newPassword.isBlank()) {
                            message = "Please fill all password fields"
                            isError = true
                            return@Button
                        }
                        if (newPassword != confirmPassword) {
                            message = "Passwords do not match"
                            isError = true
                            return@Button
                        }
                        if (newPassword.length < 6) {
                            message = "Password must be at least 6 characters"
                            isError = true
                            return@Button
                        }
                    }

                    // Update profile
                    authViewModel.updateProfile(
                        newUsername = username,
                        currentPassword = if (showPasswordFields) currentPassword else null,
                        newPassword = if (showPasswordFields) newPassword else null
                    ) { success, msg ->
                        message = msg
                        isError = !success
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = buttonColor
                ),
                shape = RoundedCornerShape(12.dp),
                enabled = !authViewModel.isLoading
            ) {
                if (authViewModel.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = Color.White
                    )
                } else {
                    Text(
                        text = "Save Changes",
                        fontSize = 16.sp,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}