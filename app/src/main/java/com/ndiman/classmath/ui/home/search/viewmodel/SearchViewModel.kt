package com.ndiman.classmath.ui.home.search.viewmodel

import androidx.lifecycle.ViewModel
import com.ndiman.classmath.data.Repository

class SearchViewModel(private val repository: Repository): ViewModel() {

    fun getSearch(query: String) = repository.search(query)
}