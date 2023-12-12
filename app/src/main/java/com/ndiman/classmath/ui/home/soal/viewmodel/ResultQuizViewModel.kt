package com.ndiman.classmath.ui.home.soal.viewmodel

import androidx.lifecycle.ViewModel
import com.ndiman.classmath.data.Repository

class ResultQuizViewModel(private val repository: Repository): ViewModel() {

    fun getResultQuiz(username: String, idQuiz: String, answers: List<String?>) = repository.getResultQuiz(username, idQuiz, answers)
}