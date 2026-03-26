package com.example.etheleduapp.screen

import android.content.Context
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

//Game Screen with a button to navigate to the score screen
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreen(currentContext: Context, navController: NavHostController, modifier: Modifier = Modifier) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Game Screen") }) }
    ){ padding ->
        Column(modifier
            .fillMaxSize()
            .padding(paddingValues = padding)
            .padding(16.dp)) {
            Button(onClick = { navController.navigate("score")
            }) {
                Text("Goto Score")
            }
        }}

}