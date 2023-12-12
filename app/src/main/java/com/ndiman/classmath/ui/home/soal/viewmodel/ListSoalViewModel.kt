package com.ndiman.classmath.ui.home.soal.viewmodel

import androidx.lifecycle.ViewModel
import com.ndiman.classmath.data.Repository

class ListSoalViewModel(private val repository: Repository): ViewModel() {

    fun getAllSoal(idGrade: String, idTutorial: String) = repository.getAllQuizzes(idGrade, idTutorial)
}