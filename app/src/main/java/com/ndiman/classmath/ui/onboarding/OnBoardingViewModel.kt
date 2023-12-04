package com.ndiman.classmath.ui.onboarding

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.ndiman.classmath.data.Repository
import com.ndiman.classmath.data.pref.UserModel

class OnBoardingViewModel(private val repository: Repository): ViewModel() {

    fun getSession(): LiveData<UserModel> = repository.getSession().asLiveData()
}