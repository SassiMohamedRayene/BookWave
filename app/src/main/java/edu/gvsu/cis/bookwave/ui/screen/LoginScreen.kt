package edu.gvsu.cis.bookwave.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import edu.gvsu.cis.bookwave.R
import edu.gvsu.cis.bookwave.navigation.Routes

@Composable
fun LoginScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    val backgroundColor = Color(0xFFF5E6D3) // Beige color
    val buttonColor = Color(0xFFE67E50) // Orange color
    val textColor = Color(0xFF2D2D2D) // Dark gray

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(24.dp)
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
                text = "Login",
                style = TextStyle(
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = textColor,
                    fontFamily = FontFamily.Monospace,
                    letterSpacing = (-0.5).sp
                ),
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Subtitle
            Text(
                text = "One subscription for Litverse,\nRecognotes, and Sparks",
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
                    text = " or Login with ",
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

            // Recovery Password Link
            Text(
                text = "Recovery Password",
                style = TextStyle(
                    fontSize = 14.sp,
                    color = textColor,
                    textDecoration = TextDecoration.Underline
                ),
                modifier = Modifier
                    .align(Alignment.End)
                    .clickable {  }
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Continue Button
            Button(
                onClick = { navController.navigate(Routes.HOME_SCREEN) },
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
                    text = "Continue",
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                    )
                )
            }
            Text(
                text = "Don't have Account? SignUp",
                style = TextStyle(
                    fontSize = 14.sp,
                    color = textColor,
                    textDecoration = TextDecoration.Underline
                ),
                modifier = Modifier
                    .clickable {  }
            )

        }
    }
}

@Composable
fun SocialSignInButton(
    text: String,
    icon: String,
    iconColor: Color,
    onClick: () -> Unit
) {
    val textColor = Color(0xFF2D2D2D)

    OutlinedButton(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp),
        shape = RoundedCornerShape(1.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = Color.Transparent,
            contentColor = textColor
        ),
        border = ButtonDefaults.outlinedButtonBorder.copy(
            width = 1.5.dp
        )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon placeholder
            when {
                icon == "G" -> {
                    Image(
                        painter = painterResource(id = R.drawable.google),
                        contentDescription = null,
                    )

                }
                icon == "f" -> {
                    Image(
                        painter = painterResource(id = R.drawable.facebook),
                        contentDescription = null,
                    )
                }
                icon == "" -> {
                    Image(
                        painter = painterResource(id = R.drawable.apple),
                        contentDescription = null,
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = text,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal
                )
            )
        }
    }
}

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    isPassword: Boolean = false,
    passwordVisible: Boolean = false,
    onPasswordVisibilityToggle: () -> Unit = {}

) {
    val textColor = Color(0xFF2D2D2D)
    val borderColor = Color(0xFF2D2D2D)

    Box(
        modifier = modifier
            .height(56.dp)
            .border(
                width = 1.5.dp,
                color = borderColor,
                shape = RoundedCornerShape(8.dp)
            )
            .clip(RoundedCornerShape(8.dp))
            .background(Color.Transparent)
            .padding(horizontal = 16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier.weight(1f),
                textStyle = TextStyle(
                    fontSize = 16.sp,
                    color = textColor
                ),
                visualTransformation = if (isPassword && !passwordVisible)
                    PasswordVisualTransformation()
                else
                    VisualTransformation.None,
                decorationBox = { innerTextField ->
                    Box {
                        if (value.isEmpty()) {
                            Text(
                                text = placeholder,
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    color = textColor.copy(alpha = 0.5f)
                                )
                            )
                        }
                        innerTextField()
                    }
                }
            )

            if (isPassword) {
                IconButton(
                    onClick = onPasswordVisibilityToggle,
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        imageVector = if (passwordVisible)
                            Icons.Default.Visibility
                        else
                            Icons.Default.VisibilityOff,
                        contentDescription = if (passwordVisible)
                            "Hide password"
                        else
                            "Show password",
                        tint = textColor.copy(alpha = 0.6f)
                    )
                }
            }
        }
    }
}
