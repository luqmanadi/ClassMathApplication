package com.ndiman.classmath.ui.home.soal.viewmodel

import androidx.lifecycle.ViewModel
import com.ndiman.classmath.data.Repository

class ListKelasSoalViewModel(private val repository: Repository): ViewModel() {

    fun getGradeSoal() = repository.getGrade()


}