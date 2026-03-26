package com.example.etheleduapp.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

//Score Screen with a button to navigate to the Landing/home screen
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScoreScreen(navController: NavHostController, modifier: Modifier = Modifier) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Score Screen") }) }
    ){ padding ->
        Column(modifier
            .fillMaxSize()
            .padding(paddingValues = padding)
            .padding(16.dp)) {
            Button(onClick = { navController.navigate("landing")
            }) {
                Text("Goto Home")
            }
        }}

}