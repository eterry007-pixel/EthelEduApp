package com.example.etheleduapp.screen

import android.content.Context
import android.media.MediaPlayer
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
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
import com.example.etheleduapp.R
import com.example.etheleduapp.helper.rememberAssetImage

//Game Screen with a button to navigate to the score screen
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreen(
    currentContext: Context,
    navController: NavHostController,
    modifier: Modifier = Modifier,
    level: String,
    playerName: String
) {

    // STATE VARIABLES
    var currentQuestion by remember { mutableIntStateOf(1) }
    var userAnswer by remember { mutableStateOf("") }

    // Track if we are showing the result of the current question
    var isAnswerChecked by remember { mutableStateOf(false) }
    var isCorrect by remember { mutableStateOf(false) }

    val totalQuestions = 5
    //keep time record
    val startTime = remember { System.currentTimeMillis() }

    // This list will store whether each answer was correct (true/false)
    val scoreResults = remember { mutableStateListOf<Boolean>() }
    // TIMER LOGIC
    var elapsedSeconds by remember { mutableIntStateOf(0) }
    androidx.compose.runtime.LaunchedEffect(Unit) {
        while (true) {
            kotlinx.coroutines.delay(1000)
            elapsedSeconds++
        }
    }

    // Shuffle the images ONCE when the level starts
    val shuffledImages = remember(level) {
        try {
            val files = currentContext.assets.list(level)?.toList() ?: emptyList()
            // Shuffle the list so the order is random, but fixed for this session
            files.shuffled()
        } catch (e: Exception) {
            emptyList()
        }
    }

    //val lever = level //level parameter for the different 3 levels
    val imageName = remember(shuffledImages, currentQuestion) {
        try {

            val files = currentContext.assets.list(level)
            if (shuffledImages.isNotEmpty()) {
                // Use (currentQuestion - 1) to get index 0, 1, 2, 3, 4
                // % size ensures we don't crash if totalQuestions > folder size
                shuffledImages[(currentQuestion - 1) % shuffledImages.size]
            } else {
                "level01_pic01_0.png" // Default image if the folder is empty
            }
        } catch (e: Exception) {
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
        topBar = { TopAppBar(title = { Text("$playerName - Level $level") }) }
    ) { innerPadding ->
        Column(
            modifier
                .fillMaxSize()
                .padding(paddingValues = innerPadding)
                .padding(16.dp)
        ) {

            // 1. Progress Text
            Text(text = "Question: $currentQuestion / $totalQuestions")
            Spacer(modifier = Modifier.height(10.dp))

            // 2. Image Display
            if (imageBitmap != null) {
                Image(
                    bitmap = imageBitmap,
                    contentDescription = "Image from assets",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(350.dp), // Slightly reduced height to fit feedback text
                    contentScale = ContentScale.Fit
                )
            } else {
                Text(text = "Image not found at: assets/$assetPath")
            }

            Spacer(modifier = Modifier.height(10.dp))

            // 3. Feedback Message (Shows ONLY after clicking Check)
            if (isAnswerChecked) {
                val correctValue = imageName.substringBeforeLast(".").substringAfterLast("_")
                Text(
                    text = if (isCorrect) "Correct! 🎉 You got 10 points" else "Incorrect!",
                    style = androidx.compose.material3.MaterialTheme.typography.headlineSmall,
                    color = if (isCorrect) androidx.compose.ui.graphics.Color(0xFF2E7D32) else androidx.compose.ui.graphics.Color.Red
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // 4. Input Field (Disabled after answer is checked)

            // Check if the input is valid (only digits and not empty)
            val isInputInvalid = userAnswer.isNotEmpty() && !userAnswer.all { it.isDigit()}
            OutlinedTextField(
                value = userAnswer,
                onValueChange = { userAnswer = it },
                label = { Text("What is the answer to this question?") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                enabled = !isAnswerChecked, // Prevents changing answer after checking
                isError = isInputInvalid, // Display error if invalid
                supportingText = {
                    if (isInputInvalid) {
                        Text(text = "Please enter a valid number.",
                            color = MaterialTheme.colorScheme.error
                         )
                    }
                },
                keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(
                    keyboardType = androidx.compose.ui.text.input.KeyboardType.Number
                )
            )

            Spacer(modifier = Modifier.height(20.dp))

            // 5. Action Button (Two-Step: Check then Next)
            Button(
                onClick = {
                    if (!isAnswerChecked) {
                        // --- STEP 1: CHECK ANSWER ---
                        val correctValue =
                            imageName.substringBeforeLast(".").substringAfterLast("_")
                        isCorrect = userAnswer.trim() == correctValue

                        playSound(currentContext, isCorrect)
                        scoreResults.add(isCorrect)

                        isAnswerChecked = true // Show feedback message
                    } else {
                        // --- STEP 2: NAVIGATION ---
                        if (currentQuestion < totalQuestions) {
                            currentQuestion++
                            userAnswer = ""
                            isAnswerChecked = false // Reset for next image
                        } else {
                            val timeTaken = (System.currentTimeMillis() - startTime) / 1000
                            //val finalScore = scoreResults.count { it }

                            // --- NEW: Calculate Points (10 per correct answer) ---
                            val correctCount = scoreResults.count { it }
                            val totalPoints = correctCount * 10
                            navController.navigate("score/$totalPoints/$totalQuestions/$timeTaken/$playerName")
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = userAnswer.isNotBlank() && userAnswer.all { it.isDigit() }|| isAnswerChecked
            ) {
                Text(
                    text = when {
                        !isAnswerChecked -> "Submit"
                        currentQuestion < totalQuestions -> "Next Question"
                        else -> "Finish"
                    }
                )
            }
        }
    }
}