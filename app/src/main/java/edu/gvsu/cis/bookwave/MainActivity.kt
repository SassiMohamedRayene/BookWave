package edu.gvsu.cis.bookwave

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import edu.gvsu.cis.bookwave.navigation.Nav
import edu.gvsu.cis.bookwave.ui.theme.BookWaveTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BookWaveTheme {
                Nav()
            }
        }
    }
}
