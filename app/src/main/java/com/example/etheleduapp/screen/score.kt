package com.example.etheleduapp.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.etheleduapp.data.AppDatabase
import com.example.etheleduapp.data.User

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScoreScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    finalScore: String,
    totalQuestions: String,
    timeTaken: String,
    userName: String,
    level: String
) {
    val context = LocalContext.current
    val db = AppDatabase.getDatabase(context)
    val userDao = db.userDao()

    // 1. Load all past scores from database
    val allUsers by userDao.getAllUsers().collectAsState(initial = emptyList())

    // 2. Save current player score ONCE when screen opens
    LaunchedEffect(Unit) {
        userDao.insertUser(
            User(
                username = userName, // Changed from name to username
                score = finalScore.toIntOrNull() ?: 0,
                total = totalQuestions.toIntOrNull() ?: 0, // Changed from totalQuestions to total
                duration = timeTaken.toIntOrNull() ?: 0, // Changed from timeTaken to duration
                level = level
            )
        )
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Score Screen") }) }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Well done, $userName!", style = MaterialTheme.typography.headlineSmall)
            Text("Current Score: $finalScore pts", style = MaterialTheme.typography.displaySmall, color = MaterialTheme.colorScheme.primary)

            Spacer(modifier = Modifier.height(20.dp))

            // --- TABLE HEADER ---
            Row(
                Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .padding(8.dp)
            ) {
                TableCell(text = "Player", weight = 0.25f, isHeader = true)
                TableCell(text = "Qs", weight = 0.15f, isHeader = true)
                TableCell(text = "Score", weight = 0.2f, isHeader = true)
                TableCell(text = "Lvl", weight = 0.15f, isHeader = true)
                TableCell(text = "Time", weight = 0.25f, isHeader = true)
            }

            // --- DATABASE RECORDS LIST ---
            // This replaces your old single PLAYER DATA ROW
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(allUsers) { user ->
                    val minutes = user.duration / 60
                    val seconds = user.duration % 60
                    val formattedTime = String.format("%02d:%02d", minutes, seconds)

                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        TableCell(text = user.username, weight = 0.25f)
                        TableCell(text = user.total.toString(), weight = 0.15f)
                        TableCell(text = user.score.toString(), weight = 0.2f)
                        TableCell(text = user.level, weight = 0.15f)
                        TableCell(text = formattedTime, weight = 0.25f)
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = {
                    navController.navigate("landing") {
                        popUpTo("landing") { inclusive = true }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Play Again")
            }
        }
    }
}

@Composable
fun RowScope.TableCell(
    text: String,
    weight: Float,
    isHeader: Boolean = false
) {
    Text(
        text = text,
        modifier = Modifier
            .weight(weight)
            .padding(4.dp),
        fontWeight = if (isHeader) FontWeight.Bold else FontWeight.Normal,
        fontSize = if (isHeader) 14.sp else 12.sp,
        maxLines = 1
    )
}