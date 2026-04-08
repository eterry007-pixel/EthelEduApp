package com.example.etheleduapp.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

//Score Screen with a button to navigate to the Landing/home screen
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScoreScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    finalScore: String,      // Added parameter
    totalQuestions: String,   // Added parameter
    timeTaken: String
) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Score Screen") }) }
    ){ padding ->
        Column(modifier
            .fillMaxSize()
            .padding(paddingValues = padding)
            .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Game Finished!",
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(20.dp))

            // Display the actual score
            Text(
                text = "Your Score: $finalScore / $totalQuestions",
                style = MaterialTheme.typography.displaySmall,
                color = MaterialTheme.colorScheme.primary
            )
            //Display Time Taken
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Time Taken: $timeTaken seconds",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.secondary
            )


            Spacer(modifier = Modifier.height(40.dp))


            Button(onClick = {
                navController.navigate("landing"){
                    popUpTo("landing") { inclusive = true }
                }
            },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Play Again")


            }
        }}

}