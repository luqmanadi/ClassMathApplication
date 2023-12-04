package com.ndiman.classmath.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.ndiman.classmath.data.Repository
import com.ndiman.classmath.data.pref.UserModel

class HomeViewModel(private val repository: Repository) : ViewModel() {


    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    fun getDetailUser() = repository.getDetailUser()
}