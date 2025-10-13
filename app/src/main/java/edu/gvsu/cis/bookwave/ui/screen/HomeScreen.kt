package edu.gvsu.cis.bookwave.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.navigation.NavController
import edu.gvsu.cis.bookwave.R
import androidx.compose.material3.Divider
import edu.gvsu.cis.bookwave.navigation.BottomNavigationBar


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(navController: NavController){


    Scaffold(

        topBar = {
            Column {
                CenterAlignedTopAppBar(
                    navigationIcon = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            IconButton(onClick = {}) {
                                Icon(Icons.Default.Menu, contentDescription = "")
                            }
                            // Ligne verticale après l'icône menu
                            Divider(
                                color = Color.Black,
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .width(1.dp)
                            )
                        }
                    },
                    title = {
                        Text(text = "BookWave")
                    },
                    actions = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            // Ligne verticale avant l'image
                            Divider(
                                color = Color.Black,
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .width(1.dp)
                            )
                            Image(
                                painter = painterResource(id = R.drawable.eminem),
                                contentDescription = "dog",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .padding(horizontal = 16.dp)
                                    .size(40.dp)
                                    .clip(CircleShape)
                                    .clickable { /* navigation vers profil */ }
                            )
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = Color(0xFFF5E6D3) // Couleur beige comme dans l'image
                    )
                )
                // Ligne horizontale noire sous le TopAppBar
                Divider(
                    color = Color.Black,
                    thickness = 1.dp
                )
            }
        },

        bottomBar = {
            BottomNavigationBar(
                navController = navController
            )
        }
    ) {


          Column (
      modifier = Modifier
          .fillMaxSize()
          .padding(8.dp),
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally

  ){
      Text(
          text = "This is HomeScreen "
      )
  }

    }
}




