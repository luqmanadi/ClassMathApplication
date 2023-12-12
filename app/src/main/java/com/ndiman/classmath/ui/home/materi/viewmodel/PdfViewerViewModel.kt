package com.ndiman.classmath.ui.home.materi.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ndiman.classmath.data.Repository
import com.ndiman.classmath.data.local.entity.FavoritMateri
import com.ndiman.classmath.data.local.entity.HistoriMateri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedInputStream
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

class PdfViewerViewModel(private val repository: Repository): ViewModel() {

    private val _pdfStream = MutableLiveData<InputStream>()
    val pdfStream: LiveData<InputStream>  = _pdfStream

    private val _pdfError = MutableLiveData<String>()
    val pdfError: LiveData<String> = _pdfError

    fun loadPdf(linkUrl: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val inputStream = downloadPdf(linkUrl)
                withContext(Dispatchers.Main) {
                    _pdfStream.value = inputStream!!
                }
            } catch (e: Exception) {
                _pdfError.postValue("Kesalahan PDF: ${e.message}")
            }
        }
    }

    private fun downloadPdf(url: String): InputStream? {
        return try {
            val urlConnection = URL(url).openConnection() as HttpURLConnection
            if (urlConnection.responseCode == 200) {
                BufferedInputStream(urlConnection.inputStream)
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun insertFavMateri(favoritMateri: FavoritMateri){
        viewModelScope.launch {
            repository.setInsertFavoritMateri(favoritMateri)
        }
    }

    fun deleteFavMateri(favoritMateri: FavoritMateri){
        viewModelScope.launch{
            repository.deleteFavoritMateri(favoritMateri)
        }
    }

    fun getIsFavoriteMateri(idTutorial: String): LiveData<Boolean> = repository.getIsFavoritMateri(idTutorial)


    fun insertHistoryMateri(historiMateri: HistoriMateri){
        viewModelScope.launch {
            repository.insertHistoryStudy(historiMateri)
        }
    }
}