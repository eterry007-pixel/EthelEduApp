package com.example.etheleduapp.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.etheleduapp.data.AppDatabase
import com.example.etheleduapp.data.User
import com.example.etheleduapp.data.UserDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {
    private val userDao: UserDao = AppDatabase.getDatabase(application).userDao()
    val allUsers: Flow<List<User>> = userDao.getAllUsers()

    fun addUser(username: String, level: String = "1", score: Int = 0, duration: Int = 0) {
        viewModelScope.launch {
            val newUser = User(
                username = username,
                level = level,
                score = score,
                duration = duration
            )
            userDao.insertUser(newUser)
        }
    }

    fun clearAll() {
        viewModelScope.launch {
            userDao.deleteAll()
        }
    }
}
