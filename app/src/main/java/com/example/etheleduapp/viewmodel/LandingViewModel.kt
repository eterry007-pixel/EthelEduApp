package com.example.etheleduapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class LandingViewModel : ViewModel() {
    // This state will now survive screen rotations
    var userName by mutableStateOf("")
}