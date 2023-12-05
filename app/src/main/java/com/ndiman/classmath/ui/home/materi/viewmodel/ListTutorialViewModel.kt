package com.ndiman.classmath.ui.home.materi.viewmodel

import androidx.lifecycle.ViewModel
import com.ndiman.classmath.data.Repository

class ListTutorialViewModel(private val repository: Repository): ViewModel() {

    fun getAllTutorial(idGrade: String) = repository.getAllTutorial(idGrade)
}