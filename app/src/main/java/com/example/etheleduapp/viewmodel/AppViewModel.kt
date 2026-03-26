package com.example.etheleduapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.etheleduapp.database.AppDao
import com.example.etheleduapp.database.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class AppViewModel(private val dao: AppDao) : ViewModel() {

    val users: Flow<List<User>> = dao.getAllUsers()
    //define other variables for user input for playing games here
    // ...

    fun addUser(username: String) {
        viewModelScope.launch {
            val user = User(username = username)
            dao.insert(user)
        }
    }

    fun clearUsers() {
        viewModelScope.launch {
            dao.deleteAll()
        }
    }
}
