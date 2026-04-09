package com.example.etheleduapp.screen


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.etheleduapp.R
import com.example.etheleduapp.viewmodel.LandingViewModel

//Landing/Home Screen with a button to navigate to the setting screen
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LandingScreen(navController: NavHostController, modifier: Modifier = Modifier, viewModel: LandingViewModel = viewModel() ) {

    //Define a state variable to hold the input text
    val userName = viewModel.userName
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = { TopAppBar(title = { Text("Welcome to the Educational App") }) }
    ){ padding ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues = padding)
            .padding(16.dp)
            .verticalScroll(scrollState), // Enable vertical scrolling
        verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // --- Welcome Screen Logo ---
            Image(
                painter = painterResource(id = R.drawable.logo_image),
                contentDescription = "logo_image",
                modifier = Modifier
                    .size(200.dp) // Set the size of the logo
                    .padding(top = 20.dp)
            )

            // --- Let's Play Text ---
            Text(
                text = "Let's play!",
                style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
                color = androidx.compose.material3.MaterialTheme.colorScheme.secondary
            )

            // --- Add the User Input field ---
            OutlinedTextField(
                value = userName,
                onValueChange = { viewModel.userName = it }, // Update the state variable when the text changes
                label = { Text("Enter your name") },
                placeholder = { Text("e.g. John Doe") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,

            )

            // --- Button to navigate and "Save" ---
            Button(
                onClick = {
                    if (userName.isNotBlank()) {
                        navController.navigate("setting/$userName")
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = userName.isNotEmpty() // Button only works if name is typed
            ) {
                Text("Save and Goto Setting")
            }
        }
}
}
