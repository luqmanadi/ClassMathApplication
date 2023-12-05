package com.ndiman.classmath.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ndiman.classmath.data.Repository
import com.ndiman.classmath.data.di.Injection
import com.ndiman.classmath.ui.auth.login.LoginViewModel
import com.ndiman.classmath.ui.auth.register.RegisterViewModel
import com.ndiman.classmath.ui.history.HistoryViewModel
import com.ndiman.classmath.ui.home.materi.viewmodel.ListKelasViewModel
import com.ndiman.classmath.ui.home.materi.viewmodel.ListTutorialViewModel
import com.ndiman.classmath.ui.home.materi.viewmodel.PdfViewerViewModel
import com.ndiman.classmath.ui.home.soal.viewmodel.ListKelasSoalViewModel
import com.ndiman.classmath.ui.home.viewmodel.HomeViewModel
import com.ndiman.classmath.ui.onboarding.OnBoardingViewModel
import com.ndiman.classmath.ui.profile.ProfileViewModel
import com.ndiman.classmath.ui.profile.detailakun.DetailAkunViewModel
import com.ndiman.classmath.ui.profile.favoritmateri.FavoriteMateriViewModel

class ViewModelFactory (private val repository: Repository): ViewModelProvider.NewInstanceFactory()  {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when{
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(repository) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(repository) as T
            }
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(repository) as T
            }
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                ProfileViewModel(repository) as T
            }
            modelClass.isAssignableFrom(OnBoardingViewModel::class.java) -> {
                OnBoardingViewModel(repository) as T
            }
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(repository) as T
            }
            modelClass.isAssignableFrom(HistoryViewModel::class.java) -> {
                HistoryViewModel(repository) as T
            }
            modelClass.isAssignableFrom(ListKelasViewModel::class.java) -> {
                ListKelasViewModel(repository) as T
            }
            modelClass.isAssignableFrom(ListKelasSoalViewModel::class.java) -> {
                ListKelasSoalViewModel(repository) as T
            }
            modelClass.isAssignableFrom(DetailAkunViewModel::class.java) -> {
                DetailAkunViewModel(repository) as T
            }
            modelClass.isAssignableFrom(FavoriteMateriViewModel::class.java) -> {
                FavoriteMateriViewModel(repository) as T
            }
            modelClass.isAssignableFrom(ListTutorialViewModel::class.java) -> {
                ListTutorialViewModel(repository) as T
            }
            modelClass.isAssignableFrom(PdfViewerViewModel::class.java) -> {
                PdfViewerViewModel(repository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        @JvmStatic
        fun getInstance(context: Context): ViewModelFactory {
            synchronized(this) {
                instance = Injection.provideRepository(context)?.let { ViewModelFactory(it) }
            }
            return instance as ViewModelFactory
        }

    }
}