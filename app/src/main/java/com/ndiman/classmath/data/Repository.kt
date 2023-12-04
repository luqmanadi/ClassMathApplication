package com.ndiman.classmath.data

import android.util.Log
import androidx.lifecycle.liveData
import com.google.gson.Gson
import com.ndiman.classmath.data.pref.UserModel
import com.ndiman.classmath.data.pref.UserPreference
import com.ndiman.classmath.data.remote.response.ErrorResponse
import com.ndiman.classmath.data.remote.retrofit.ApiService
import com.ndiman.classmath.data.remote.retrofit.LoginRequest
import com.ndiman.classmath.data.remote.retrofit.RegisterRequest
import com.ndiman.classmath.data.remote.retrofit.UpdateRequest
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException

class Repository private constructor(
    private val apiService: ApiService,
    private val userPreference: UserPreference
){

    fun registerUser(username: String, password: String, name: String) = liveData {
        emit(Result.Loading)
        try {
            val registerRequest = RegisterRequest(username, password, name)
            val response = apiService.register(registerRequest)
            emit(Result.Success(response))
        } catch (e: HttpException) {
            val jsonString = e.response()?.errorBody()?.string()
            Log.d(TAG, "StoryRepository: $jsonString")
            val errorBody = Gson().fromJson(jsonString, ErrorResponse::class.java)
            val errorMessage = errorBody.errors
            emit(Result.Error(errorMessage))
        } catch (e: Exception) {
            emit(Result.Error("Lost Connection"))
        }
    }

    fun loginUser(username: String, password: String) = liveData {
        emit(Result.Loading)
        try {
            val loginRequest = LoginRequest(username, password)
            val response = apiService.login(loginRequest)
            emit(Result.Success(response))
        } catch (e: HttpException) {
            val jsonString = e.response()?.errorBody()?.string()
            Log.d(TAG, "StoryRepository: $jsonString")
            val errorBody = Gson().fromJson(jsonString, ErrorResponse::class.java)
            val errorMessage = errorBody.errors
            emit(Result.Error(errorMessage))
        } catch (e: Exception) {
            emit(Result.Error("Lost Connection"))
        }
    }

    fun getDetailUser() = liveData{
        emit(Result.Loading)
        try {
            val response = apiService.getUser()
            emit(Result.Success(response))
        } catch (e: HttpException) {
            val jsonString = e.response()?.errorBody()?.string()
            Log.d(TAG, "StoryRepository: $jsonString")
            val errorBody = Gson().fromJson(jsonString, ErrorResponse::class.java)
            val errorMessage = errorBody.errors
            emit(Result.Error(errorMessage))
        } catch (e: Exception) {
            emit(Result.Error("Lost Connection"))
        }
    }

    fun updateUser(name: String, password: String) = liveData {
        emit(Result.Loading)
        try {
            val loginRequest = UpdateRequest(name, password)
            val response = apiService.updateUser(loginRequest)
            emit(Result.Success(response))
        }catch (e: HttpException) {
            val jsonString = e.response()?.errorBody()?.string()
            Log.d(TAG, "StoryRepository: $jsonString")
            val errorBody = Gson().fromJson(jsonString, ErrorResponse::class.java)
            val errorMessage = errorBody.errors
            emit(Result.Error(errorMessage))
        } catch (e: Exception) {
            emit(Result.Error("Lost Connection"))
        }
    }

    fun getGrade() = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getGrade()
            emit(Result.Success(response))
        } catch (e: HttpException) {
            val jsonString = e.response()?.errorBody()?.string()
            Log.d(TAG, "StoryRepository: $jsonString")
            val errorBody = Gson().fromJson(jsonString, ErrorResponse::class.java)
            val errorMessage = errorBody.errors
            emit(Result.Error(errorMessage))
        } catch (e: Exception) {
            emit(Result.Error("Lost Connection"))
        }
    }

    fun getAllTutorial(idGrade: String) = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getAllTutorial(idGrade)
            emit(Result.Success(response))
        } catch (e: HttpException) {
            val jsonString = e.response()?.errorBody()?.string()
            Log.d(TAG, "StoryRepository: $jsonString")
            val errorBody = Gson().fromJson(jsonString, ErrorResponse::class.java)
            val errorMessage = errorBody.errors
            emit(Result.Error(errorMessage))
        } catch (e: Exception) {
            emit(Result.Error("Lost Connection"))
        }
    }


    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun logout() = userPreference.logout()

    fun getThemeSeting(): Flow<Boolean> {
        return userPreference.getThemeSetting()
    }

    suspend fun saveThemeSetting(isDarkModeActive: Boolean) =
        userPreference.saveThemeSetting(isDarkModeActive)

    suspend fun saveSession(userModel: UserModel) = userPreference.saveSession(userModel)

    companion object {

        private const val TAG = "Repository"

        @Volatile
        private var instance: Repository? = null
        fun getInstance(
            apiService: ApiService,
            userPreference: UserPreference
        ): Repository? {
            synchronized(this) {
                instance = Repository(apiService, userPreference)
            }
            return instance
        }

    }
}