package com.ndiman.classmath.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.ndiman.classmath.data.local.entity.FavoritMateri
import com.ndiman.classmath.data.local.entity.HistoriMateri
import com.ndiman.classmath.data.local.room.ClassMathDao
import com.ndiman.classmath.data.pref.UserModel
import com.ndiman.classmath.data.pref.UserPreference
import com.ndiman.classmath.data.remote.response.ErrorResponse
import com.ndiman.classmath.data.remote.retrofit.AnswerRequest
import com.ndiman.classmath.data.remote.retrofit.ApiService
import com.ndiman.classmath.data.remote.retrofit.LoginRequest
import com.ndiman.classmath.data.remote.retrofit.RegisterRequest
import com.ndiman.classmath.data.remote.retrofit.UpdateRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import retrofit2.HttpException

class Repository private constructor(
    private val apiService: ApiService,
    private val userPreference: UserPreference,
    private val dao: ClassMathDao
){

    fun registerUser(username: String, password: String, name: String) = liveData {
        emit(Result.Loading)
        try {
            val registerRequest = RegisterRequest(username, password, name)
            val response = apiService.register(registerRequest)
            emit(Result.Success(response))
        } catch (e: HttpException) {
            val jsonString = e.response()?.errorBody()?.string()
            Log.d(TAG, "Repository: $jsonString")
            try {
                val errorBody = Gson().fromJson(jsonString, ErrorResponse::class.java)
                val errorMessage = errorBody.errors
                emit(Result.Error(errorMessage))
            } catch (jsonException: JsonSyntaxException) {
                Log.e(TAG, "JsonSyntaxException: ${jsonException.message}")
                emit(Result.Error("Unexpected response format"))
            }
        }
        catch (e: Exception) {
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
            Log.d(TAG, "Repository: $jsonString")
            try {
                val errorBody = Gson().fromJson(jsonString, ErrorResponse::class.java)
                val errorMessage = errorBody.errors
                emit(Result.Error(errorMessage))
            } catch (jsonException: JsonSyntaxException) {
                Log.e(TAG, "JsonSyntaxException: ${jsonException.message}")
                emit(Result.Error("Unexpected response format"))
            }
        }
        catch (e: Exception) {
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
            Log.d(TAG, "Repository: $jsonString")
            try {
                val errorBody = Gson().fromJson(jsonString, ErrorResponse::class.java)
                val errorMessage = errorBody.errors
                emit(Result.Error(errorMessage))
            } catch (jsonException: JsonSyntaxException) {
                Log.e(TAG, "JsonSyntaxException: ${jsonException.message}")
                emit(Result.Error("Unexpected response format"))
            }
        }
        catch (e: Exception) {
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
            Log.d(TAG, "Repository: $jsonString")
            try {
                val errorBody = Gson().fromJson(jsonString, ErrorResponse::class.java)
                val errorMessage = errorBody.errors
                emit(Result.Error(errorMessage))
            } catch (jsonException: JsonSyntaxException) {
                Log.e(TAG, "JsonSyntaxException: ${jsonException.message}")
                emit(Result.Error("Unexpected response format"))
            }
        }
        catch (e: Exception) {
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
            Log.d(TAG, "Repository: $jsonString")
            try {
                val errorBody = Gson().fromJson(jsonString, ErrorResponse::class.java)
                val errorMessage = errorBody.errors
                emit(Result.Error(errorMessage))
            } catch (jsonException: JsonSyntaxException) {
                Log.e(TAG, "JsonSyntaxException: ${jsonException.message}")
                emit(Result.Error("Unexpected response format"))
            }
        }
        catch (e: Exception) {
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
            Log.d(TAG, "Repository: $jsonString")
            try {
                val errorBody = Gson().fromJson(jsonString, ErrorResponse::class.java)
                val errorMessage = errorBody.errors
                emit(Result.Error(errorMessage))
            } catch (jsonException: JsonSyntaxException) {
                Log.e(TAG, "JsonSyntaxException: ${jsonException.message}")
                emit(Result.Error("Unexpected response format"))
            }
        }
        catch (e: Exception) {
            emit(Result.Error("Lost Connection"))
        }
    }


    fun getAllQuizzes(idGrade: String, idTutorial: String) = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getAllQuizzes(idGrade, idTutorial)
            emit(Result.Success(response))
        } catch (e: HttpException) {
            val jsonString = e.response()?.errorBody()?.string()
            Log.d(TAG, "Repository: $jsonString")
            try {
                val errorBody = Gson().fromJson(jsonString, ErrorResponse::class.java)
                val errorMessage = errorBody.errors
                emit(Result.Error(errorMessage))
            } catch (jsonException: JsonSyntaxException) {
                Log.e(TAG, "JsonSyntaxException: ${jsonException.message}")
                emit(Result.Error("Unexpected response format"))
            }
        }
        catch (e: Exception) {
            emit(Result.Error("Lost Connection"))
        }
    }

    fun getIdQuizzes(idGrade: String, idTutorial: String, idQuiz: String) = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getIdQuizzes(idGrade, idTutorial, idQuiz)
            emit(Result.Success(response))
        }catch (e: HttpException) {
            val jsonString = e.response()?.errorBody()?.string()
            Log.d(TAG, "Repository: $jsonString")
            try {
                val errorBody = Gson().fromJson(jsonString, ErrorResponse::class.java)
                val errorMessage = errorBody.errors
                emit(Result.Error(errorMessage))
            } catch (jsonException: JsonSyntaxException) {
                Log.e(TAG, "JsonSyntaxException: ${jsonException.message}")
                emit(Result.Error("Unexpected response format"))
            }
        }
        catch (e: Exception) {
            emit(Result.Error("Lost Connection"))
        }
    }

    fun getResultQuiz(username: String, idQuiz: String, answers: List<String?>) = liveData {
        emit(Result.Loading)
        try {
            val answerRequest = AnswerRequest(answers)
            val response = apiService.resultQuiz(username,idQuiz, answerRequest)
            emit(Result.Success(response))
        }catch (e: HttpException) {
            val jsonString = e.response()?.errorBody()?.string()
            Log.d(TAG, "Repository: $jsonString")
            try {
                val errorBody = Gson().fromJson(jsonString, ErrorResponse::class.java)
                val errorMessage = errorBody.errors
                emit(Result.Error(errorMessage))
            } catch (jsonException: JsonSyntaxException) {
                Log.e(TAG, "JsonSyntaxException: ${jsonException.message}")
                emit(Result.Error("Unexpected response format"))
            }
        }
        catch (e: Exception) {
            emit(Result.Error("Lost Connection"))
        }
    }


    fun search(search: String) = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.search(search)
            emit(Result.Success(response))
        }catch (e: HttpException) {
            val jsonString = e.response()?.errorBody()?.string()
            Log.d(TAG, "Repository: $jsonString")
            try {
                val errorBody = Gson().fromJson(jsonString, ErrorResponse::class.java)
                val errorMessage = errorBody.errors
                emit(Result.Error(errorMessage))
            } catch (jsonException: JsonSyntaxException) {
                Log.e(TAG, "JsonSyntaxException: ${jsonException.message}")
                emit(Result.Error("Unexpected response format"))
            }
        }
        catch (e: Exception) {
            emit(Result.Error("Lost Connection"))
        }
    }

    fun getLeaderboard() = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getLeaderboard()
            emit(Result.Success(response))
        }catch (e: HttpException) {
            val jsonString = e.response()?.errorBody()?.string()
            Log.d(TAG, "Repository: $jsonString")
            try {
                val errorBody = Gson().fromJson(jsonString, ErrorResponse::class.java)
                val errorMessage = errorBody.errors
                emit(Result.Error(errorMessage))
            } catch (jsonException: JsonSyntaxException) {
                Log.e(TAG, "JsonSyntaxException: ${jsonException.message}")
                emit(Result.Error("Unexpected response format"))
            }
        }
        catch (e: Exception) {
            emit(Result.Error("Lost Connection"))
        }
    }

    fun getAllFavoritMateri(): LiveData<List<FavoritMateri>>{
        return dao.getFavoriteMateri()
    }

    suspend fun setInsertFavoritMateri(favMateri: FavoritMateri){
        coroutineScope {
            launch(Dispatchers.IO){
                dao.insertMateriFavorit(favMateri)
            }
        }
    }


    suspend fun deleteFavoritMateri(favMateri: FavoritMateri){
        coroutineScope {
            launch(Dispatchers.IO){
                dao.deleteMateriFavorit(favMateri)
            }
        }
    }

    fun getIsFavoritMateri(idTutorial: String): LiveData<Boolean> = dao.isFavoriteMateri(idTutorial)


    fun getAllHistoryStudy(): LiveData<List<HistoriMateri>> {
        return dao.getHistoryMateri()
    }

    suspend fun insertHistoryStudy(historiMateri: HistoriMateri){
        coroutineScope {
            launch(Dispatchers.IO) {
                dao.insertHistoryMateri(historiMateri)
            }
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
            userPreference: UserPreference,
            dao: ClassMathDao
        ): Repository? {
            synchronized(this) {
                instance = Repository(apiService, userPreference, dao)
            }
            return instance
        }

    }
}