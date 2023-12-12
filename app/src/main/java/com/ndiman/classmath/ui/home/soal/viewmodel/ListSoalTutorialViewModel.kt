package com.ndiman.classmath.ui.home.soal.viewmodel

import androidx.lifecycle.ViewModel
import com.ndiman.classmath.data.Repository

class ListSoalTutorialViewModel(private val repository: Repository): ViewModel() {

    fun getAllTutorial(idGrade: String) = repository.getAllTutorial(idGrade)
}