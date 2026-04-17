package com.example.etheleduapp.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

//Setting Screen with a button to navigate to the game screen
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(navController: NavHostController, userName: String = "Player", modifier: Modifier = Modifier) {

    // --- dropdown option for game level 1, 2 or 3
    val levels = listOf("1", "2", "3")
    var expanded by remember { mutableStateOf(false) }
    var selectedLevel by remember { mutableStateOf(levels[0]) }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Setting Screen") }) }
    ){ padding ->
        Column(modifier
            .fillMaxSize()
            .padding(paddingValues = padding)
            .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
            // --- Welcome Text to user ---
            Text(
                text = "Hi $userName,",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary
            )

            Text(
                text = "Please select your level:",
                style = MaterialTheme.typography.bodyLarge
            )
            // --- Dropdown option for the levels ---
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    value = "Level $selectedLevel",
                    onValueChange = {},
                    readOnly = true, // User cannot type, only select
                    label = { Text("Level") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier
                        .menuAnchor() // Enables positioning
                        .fillMaxWidth()
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    levels.forEach { level ->
                        DropdownMenuItem(
                            text = { Text("Level $level") },
                            onClick = {
                                selectedLevel = level
                                expanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f)) // Pushes the button to the bottom


            Button(onClick = {
                // --- Navigate to the game screen with the selected level ---This sends the folder name (1, 2, or 3) to the next screen
                navController.navigate("game/$selectedLevel/$userName")
            },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Start Game - Level $selectedLevel")
            }
        }}

}