package com.ndiman.classmath.ui.profile.detailakun

import androidx.lifecycle.ViewModel
import com.ndiman.classmath.data.Repository

class DetailAkunViewModel(private val repository: Repository): ViewModel() {

    fun getDetailAkun() = repository.getDetailUser()
    fun updateUser(name: String, password: String) = repository.updateUser(name, password)
}