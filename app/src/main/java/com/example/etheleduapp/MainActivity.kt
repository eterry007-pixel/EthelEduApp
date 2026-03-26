@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.etheleduapp

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
import com.example.etheleduapp.screen.LandingScreen
import com.example.etheleduapp.screen.ScoreScreen
import com.example.etheleduapp.screen.SettingScreen
import com.example.etheleduapp.ui.theme.EthelEduAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EthelEduAppTheme {
                AppNav()
                }
            }
        }
    }

//setup the 4 navigation routes
@Composable
fun AppNav() {
    //obtain navController
    val navController = rememberNavController()
    //set the navHost and the routes
    NavHost(navController, startDestination = "landing"){
        //define the home route (landing)
        composable("landing"){LandingScreen(navController)
        }
        //define the activity/game route
        composable("game"){GameScreen(navController)
        }
        //define the setting route
        composable("setting"){SettingScreen(navController)
        }
        //define the score route
        composable("score"){ScoreScreen(navController)
        }

    }

}