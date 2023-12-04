package com.ndiman.classmath.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.ndiman.classmath.data.Repository
import com.ndiman.classmath.data.pref.UserModel

class MainViewModel(private val repository: Repository): ViewModel() {

    fun getThemeSetting(): LiveData<Boolean> {
        return repository.getThemeSeting().asLiveData()
    }

    fun getSession(): LiveData<UserModel> = repository.getSession().asLiveData()
}