@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.etheleduapp

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.etheleduapp.screen.GameScreen
import com.example.etheleduapp.screen.ImageDisplayScreen
import com.example.etheleduapp.screen.LandingScreen
import com.example.etheleduapp.screen.ScoreScreen
import com.example.etheleduapp.screen.SettingScreen
import com.example.etheleduapp.screen.TestDBScreen
import com.example.etheleduapp.ui.theme.EthelEduAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val currentContext = applicationContext
        setContent {
            EthelEduAppTheme {
                AppNav(currentContext)
                }
            }
        }
    }

//setup the 4 navigation routes
@Composable
fun AppNav(currentContext: Context) {
    //obtain navController
    val navController = rememberNavController()
    //set the navHost and the routes
    NavHost(navController, startDestination = "landing"){
        //define the home route (landing)
        composable("landing"){LandingScreen(navController) //page 1
        }

        //define the setting route
        composable("setting/{userName}"){backStackEntry ->
            // Extract the name from the path. If it's missing, use "Player"
            val name = backStackEntry.arguments?.getString("userName") ?: "Player"

            SettingScreen(
                navController = navController,
                userName = name // Pass the actual name here
            ) // this is page 2
        }


        //define the activity/game route
        // define the activity/game route to accept a level parameter
        composable("game/{level}") { backStackEntry ->
            // 1. Extract the level (e.g., "1", "2", or "3") from the navigation path
            val selectedLevel = backStackEntry.arguments?.getString("level") ?: "1"

            // 2. Pass that level string into your GameScreen
            GameScreen(
                currentContext = currentContext,
                navController = navController,
                level = selectedLevel // Make sure to update your GameScreen function signature to accept this
            )
        }

        //define the score route
        composable("score/{finalScore}/{total}/{time}") { backStackEntry ->
            // Extract the values from the path
            val score = backStackEntry.arguments?.getString("finalScore") ?: "0"
            val total = backStackEntry.arguments?.getString("total") ?: "5"
            val time = backStackEntry.arguments?.getString("time") ?: "0"

            ScoreScreen(
                navController = navController,
                finalScore = score,
                totalQuestions = total,
                timeTaken = time
            )
        }
        //define the testDB route
        composable("testDB"){TestDBScreen(currentContext = currentContext)
        }
        // define the imageDisplay route:
        composable("imageDisplay") {
            ImageDisplayScreen(
                context = currentContext,
                folder = "1",
                imageName = "level01_pic01_0.png"
            )
        }
    }
}