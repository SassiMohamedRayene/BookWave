package edu.gvsu.cis.bookwave.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import edu.gvsu.cis.bookwave.data.model.FakeUserData
import edu.gvsu.cis.bookwave.data.model.Message
import edu.gvsu.cis.bookwave.viewmodel.MessageViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ChatScreen(
    navController: NavController,
    userId: Int,
    viewModel: MessageViewModel = viewModel()
) {
    val messages by viewModel.currentMessages.collectAsState()
    val currentUser by viewModel.currentChatUser.collectAsState()
    var messageText by remember { mutableStateOf("") }
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    // Charger les messages pour cet utilisateur
    LaunchedEffect(userId) {
        viewModel.loadMessagesForUser(userId)
    }

    // Auto-scroll vers le bas quand de nouveaux messages arrivent
    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            coroutineScope.launch {
                listState.animateScrollToItem(messages.size - 1)
            }
        }
    }

    Scaffold(
        topBar = {
            Column {
                CenterAlignedTopAppBar(
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                Icons.Default.ArrowBack,
                                contentDescription = "Back",
                                tint = Color.Black
                            )
                        }
                    },
                    title = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFFE67E50)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = currentUser?.username?.first()?.uppercase() ?: "",
                                    style = TextStyle(
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White,
                                        fontFamily = FontFamily.Monospace
                                    )
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = currentUser?.username ?: "",
                                style = TextStyle(
                                    fontSize = 20.sp,
                                    color = Color.Black,
                                    fontFamily = FontFamily.Monospace,
                                    letterSpacing = (-0.5).sp
                                )
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
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFFF5E6D3))
        ) {
            // Messages list
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                state = listState,
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(messages) { message ->
                    MessageBubble(
                        message = message,
                        isCurrentUser = message.senderId == FakeUserData.currentUser.id
                    )
                }
            }

            // Input field
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF5E6D3))
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = messageText,
                    onValueChange = { messageText = it },
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(24.dp)),
                    placeholder = {
                        Text(
                            "Type a message...",
                            style = TextStyle(
                                fontFamily = FontFamily.Monospace,
                                fontSize = 14.sp
                            )
                        )
                    },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    textStyle = TextStyle(
                        fontFamily = FontFamily.Monospace,
                        fontSize = 14.sp
                    ),
                    maxLines = 4
                )

                Spacer(modifier = Modifier.width(8.dp))

                IconButton(
                    onClick = {
                        if (messageText.isNotBlank()) {
                            viewModel.sendMessage(userId, messageText)
                            messageText = ""
                        }
                    },
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFE67E50))
                ) {
                    Icon(
                        Icons.Default.Send,
                        contentDescription = "Send",
                        tint = Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun MessageBubble(
    message: Message,
    isCurrentUser: Boolean
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = if (isCurrentUser) Alignment.End else Alignment.Start
    ) {
        Box(
            modifier = Modifier
                .clip(
                    RoundedCornerShape(
                        topStart = 16.dp,
                        topEnd = 16.dp,
                        bottomStart = if (isCurrentUser) 16.dp else 4.dp,
                        bottomEnd = if (isCurrentUser) 4.dp else 16.dp
                    )
                )
                .background(
                    if (isCurrentUser) Color(0xFFE67E50) else Color.White
                )
                .padding(horizontal = 16.dp, vertical = 10.dp)
                .widthIn(max = 280.dp)
        ) {
            Column {
                Text(
                    text = message.content,
                    style = TextStyle(
                        fontSize = 14.sp,
                        color = if (isCurrentUser) Color.White else Color.Black,
                        fontFamily = FontFamily.Monospace
                    )
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = formatMessageTime(message.timestamp),
                    style = TextStyle(
                        fontSize = 11.sp,
                        color = if (isCurrentUser) Color.White.copy(alpha = 0.7f) else Color.Black.copy(alpha = 0.5f),
                        fontFamily = FontFamily.Monospace
                    )
                )
            }
        }
    }
}

fun formatMessageTime(timestamp: Long): String {
    val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
    return sdf.format(Date(timestamp))
}