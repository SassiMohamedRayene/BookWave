package edu.gvsu.cis.bookwave.ui.utils

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*

@Composable
fun RequestNotificationPermission() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        val launcher = rememberLauncherForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            // Handle permission result if needed
        }

        LaunchedEffect(Unit) {
            launcher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }
}