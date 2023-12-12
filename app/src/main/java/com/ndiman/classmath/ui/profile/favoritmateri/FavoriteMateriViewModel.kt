package com.ndiman.classmath.ui.profile.favoritmateri

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ndiman.classmath.data.Repository
import com.ndiman.classmath.data.local.entity.FavoritMateri

class FavoriteMateriViewModel(private val repository: Repository): ViewModel() {
    fun getFavoritMateri(): LiveData<List<FavoritMateri>> = repository.getAllFavoritMateri()

}