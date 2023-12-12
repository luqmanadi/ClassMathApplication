package com.ndiman.classmath.ui.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ndiman.classmath.data.Repository
import com.ndiman.classmath.data.local.entity.HistoriMateri

class HistoryViewModel(private val repository: Repository) : ViewModel() {

    fun getAllHistoryStudy(): LiveData<List<HistoriMateri>> = repository.getAllHistoryStudy()

}