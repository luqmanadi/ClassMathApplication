package com.ndiman.classmath.ui.home.soal.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.ndiman.classmath.data.Repository
import com.ndiman.classmath.data.pref.UserModel

class SoalViewModel(private val repository: Repository): ViewModel() {

    private var answers: MutableList<String?> = mutableListOf()
    private var questSize: Int = 0

    fun getSession(): LiveData<UserModel> = repository.getSession().asLiveData()

    fun initQuestSize(size: Int) {
        questSize = size
        // Inisialisasi answers dengan null sesuai dengan jumlah pertanyaan
        answers = MutableList(questSize) { null }
    }
    fun getQuizezz(idGrade: String, idTutorial: String, idQuiz: String) = repository.getIdQuizzes(idGrade, idTutorial, idQuiz)

    fun saveAnswer(index: Int, answer: String?){
        if (index >= 0 && index < answers.size) {
            // Simpan jawaban ke dalam variabel answers
            answers[index] = answer
        }
        Log.d("ViewModel", "Cek Save Choice: $answers")
    }

    fun getAnswers(): List<String?>{
        return answers
    }
}