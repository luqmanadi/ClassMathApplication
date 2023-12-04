package com.ndiman.classmath.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.ndiman.classmath.data.Repository
import kotlinx.coroutines.launch

class ProfileViewModel(private val repository: Repository) : ViewModel() {

    fun logout(){
        viewModelScope.launch {
            repository.logout()
        }
    }

    fun getThemeSetting(): LiveData<Boolean> {
        return repository.getThemeSeting().asLiveData()
    }

    fun saveThemeSetting(isDarkModeActive: Boolean){
        viewModelScope.launch {
            repository.saveThemeSetting(isDarkModeActive)
        }
    }

    fun getDetailUser() = repository.getDetailUser()
}