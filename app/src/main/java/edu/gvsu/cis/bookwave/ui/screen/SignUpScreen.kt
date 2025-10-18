package edu.gvsu.cis.bookwave.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import edu.gvsu.cis.bookwave.R
import edu.gvsu.cis.bookwave.navigation.Routes

@Composable
fun SignUpScreen(navController: NavController) {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    val backgroundColor = Color(0xFFF5E6D3) // Beige color
    val buttonColor = Color(0xFFE67E50) // Orange color
    val textColor = Color(0xFF2D2D2D) // Dark gray

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(start = 24.dp, bottom = 24.dp, end = 24.dp, top = 40.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Title
            Text(
                text = "Sign Up",
                style = TextStyle(
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = textColor,
                    fontFamily = FontFamily.Monospace,
                    letterSpacing = (-0.5).sp
                ),
            )

            // Subtitle
            Text(
                text = "Create your account to access\nthousands of audiobooks",
                style = TextStyle(
                    fontSize = 16.sp,
                    color = textColor,
                    fontFamily = FontFamily.Monospace,
                    textAlign = TextAlign.Center,
                    lineHeight = 22.sp
                ),
                modifier = Modifier.padding(bottom = 20.dp)
            )

            SocialSignInButton(
                text = "Continue with Google",
                icon = "G",
                iconColor = Color(0xFF4285F4),
                onClick = {}
            )

            SocialSignInButton(
                text = "Continue with Facebook",
                icon = "f",
                iconColor = Color(0xFF1877F2),
                onClick = {}
            )

            SocialSignInButton(
                text = "Continue with Apple",
                icon = "",
                iconColor = Color.Black,
                onClick = {}
            )

            // Divider with text
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Divider(
                    modifier = Modifier.weight(1f),
                    color = textColor.copy(alpha = 0.3f),
                    thickness = 1.dp
                )
                Text(
                    text = " or Sign Up with ",
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = FontFamily.Monospace,
                        color = textColor
                    )
                )
                Divider(
                    modifier = Modifier.weight(1f),
                    color = textColor.copy(alpha = 0.3f),
                    thickness = 1.dp
                )
            }

            // Username Input
            CustomTextField(
                value = username,
                onValueChange = { username = it },
                placeholder = "Username",
                modifier = Modifier.fillMaxWidth()
            )

            // Email Input
            CustomTextField(
                value = email,
                onValueChange = { email = it },
                placeholder = "Email address",
                modifier = Modifier.fillMaxWidth()
            )

            // Password Input
            CustomTextField(
                value = password,
                onValueChange = { password = it },
                placeholder = "Password",
                isPassword = true,
                passwordVisible = passwordVisible,
                onPasswordVisibilityToggle = { passwordVisible = !passwordVisible },
                modifier = Modifier.fillMaxWidth()
            )

            // Confirm Password Input
            CustomTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                placeholder = "Confirm Password",
                isPassword = true,
                passwordVisible = confirmPasswordVisible,
                onPasswordVisibilityToggle = { confirmPasswordVisible = !confirmPasswordVisible },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Sign Up Button
            Button(
                onClick = {
                    // TODO: Add Firebase sign up logic here
                    navController.navigate(Routes.HOME_SCREEN)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(1.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = buttonColor,
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "Sign Up",
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                    )
                )
            }

            // Navigate to Login
            Text(
                text = "Already have an account? Login",
                style = TextStyle(
                    fontSize = 14.sp,
                    color = textColor,
                    textDecoration = TextDecoration.Underline
                ),
                modifier = Modifier
                    .clickable { navController.navigate(Routes.LOGIN_SCREEN) }
            )
        }
    }
}