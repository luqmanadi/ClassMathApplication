package com.ndiman.classmath.ui.auth.register

import androidx.lifecycle.ViewModel
import com.ndiman.classmath.data.Repository

class RegisterViewModel(private val repository: Repository): ViewModel() {

    fun userRegister(username: String, password: String, name: String) = repository.registerUser(username, password, name)
}