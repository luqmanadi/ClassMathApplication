package com.ndiman.classmath.ui.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ndiman.classmath.data.Repository
import com.ndiman.classmath.data.pref.UserModel
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: Repository): ViewModel() {

    fun loginUser(username: String, password: String) = repository.loginUser(username, password)

    fun saveSession(userModel: UserModel){
        viewModelScope.launch {
            repository.saveSession(userModel)
        }
    }
}