package com.ndiman.classmath.ui.home.materi.viewmodel

import androidx.lifecycle.ViewModel
import com.ndiman.classmath.data.Repository

class ListKelasViewModel(private val repository: Repository): ViewModel() {

    fun getGradeMateri() = repository.getGrade()
}