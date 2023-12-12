package com.ndiman.classmath.data.di

import android.content.Context
import com.ndiman.classmath.data.Repository
import com.ndiman.classmath.data.local.room.ClassMathRoomDatabase
import com.ndiman.classmath.data.pref.UserPreference
import com.ndiman.classmath.data.pref.dataStore
import com.ndiman.classmath.data.remote.retrofit.ApiConfig
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {
    fun provideRepository(context: Context): Repository? {
        val pref = UserPreference.getInstance(context.dataStore)
        val user = runBlocking { pref.getSession().first() }
        val apiService = ApiConfig.getApiService(user.token)
        val databse = ClassMathRoomDatabase.getInstance(context)
        val dao = databse.classMathDao()
        return Repository.getInstance(apiService, pref, dao)
    }
}