package com.example.etheleduapp.screen

import android.content.Context
import android.media.MediaPlayer
import com.example.etheleduapp.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.etheleduapp.helper.rememberAssetImage

//Game Screen with a button to navigate to the score screen
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreen(
    currentContext: Context,
    navController: NavHostController,
    modifier: Modifier = Modifier,
    level: String,
) {

    // --- 1. NEW STATE VARIABLES ---
    var currentQuestion by remember { mutableIntStateOf(1) }
    var userAnswer by remember { mutableStateOf("") }
    val totalQuestions = 5
    //keep time record
    val startTime = remember { System.currentTimeMillis() }

    // This list will store whether each answer was correct (true/false)
    val scoreResults = remember { mutableStateListOf<Boolean>() }

    //val lever = level //level parameter for the different 3 levels
    val imageName = remember(level, currentQuestion) {
        try {

            val files = currentContext.assets.list(level)
            if (!files.isNullOrEmpty()) {
                files.random() // Pick a random image from the folder
            } else {
                "level01_pic01_0.png" // Default image if the folder is empty
            }
            }catch (e: Exception){
                "level01_pic01_0.png" // Default image if the folder is empty

        }
    }

    val assetPath = "$level/$imageName" //path to the image
    val imageBitmap = rememberAssetImage(currentContext, assetPath)

    // Helper function to play sound
    fun playSound(context: Context, isCorrect: Boolean) {
        val soundRes = if (isCorrect) R.raw.correct_sound else R.raw.wrong_sound
        val mediaPlayer = MediaPlayer.create(context, soundRes)
        mediaPlayer.setOnCompletionListener { it.release() } // Free memory when done
        mediaPlayer.start()
    }


    Scaffold(
        topBar = { TopAppBar(title = { Text("Game Screen - Level $level") }) }
    ){
        innerPadding ->
        Column(modifier
            .fillMaxSize()
            .padding(paddingValues = innerPadding)
            .padding(16.dp)) {
                // Display progress
                Text(text = "Question: $currentQuestion / $totalQuestions")
                Spacer(modifier = Modifier.height(10.dp))

                //Display Image
                if (imageBitmap != null) {
                    Image(
                        bitmap = imageBitmap,
                        contentDescription = "Image from assets",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(400.dp),
                        contentScale = ContentScale.Fit
                    )
                } else {
                    // Helpful error message if the file is missing in a specific folder
                    Text(text = "Image not found at: assets/$assetPath")
                }

            Spacer(modifier = Modifier.height(20.dp))


            // --- 3. NEW USER INPUT FIELD ---
            OutlinedTextField(
                value = userAnswer,
                onValueChange = { userAnswer = it },
                label = { Text("What is the answer to this question?") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(20.dp))


            Button(
                onClick = {
                    // --- LOGIC TO CHECK ANSWER ---
                    // 1. Extract number from filename (e.g. "level01_pic01_12.png" -> "12")
                    // We take the part between the last '_' and the '.'
                    val correctValue = imageName
                        .substringBeforeLast(".")
                        .substringAfterLast("_")

                    // 2. Compare user input to the extracted number
                    val isCorrect = userAnswer.trim() == correctValue
                    scoreResults.add(isCorrect)

                    // --- NEW: Play the Sound ---
                    playSound(currentContext, isCorrect)

                    // --- NAVIGATION LOGIC ---
                    if (currentQuestion < totalQuestions) {
                        currentQuestion++
                        userAnswer = ""
                    } else {
                        // Calculate final score
                        val timeTaken = (System.currentTimeMillis() - startTime) / 1000
                        val finalScore = scoreResults.count { it }
                        // Navigate to score screen and pass the result
                        navController.navigate("score/$finalScore/$totalQuestions/$timeTaken")
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = userAnswer.isNotBlank()
            ) {
                Text(if (currentQuestion < totalQuestions) "Next Question" else "Finish")
            }
        }
    }
}