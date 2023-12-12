package com.ndiman.classmath.data.remote.retrofit

import com.ndiman.classmath.data.remote.response.GetAllQuizzResponse
import com.ndiman.classmath.data.remote.response.GetAllTutorialResponse
import com.ndiman.classmath.data.remote.response.GetGradeResponse
import com.ndiman.classmath.data.remote.response.GetIdQuizzResponse
import com.ndiman.classmath.data.remote.response.GetUserResponse
import com.ndiman.classmath.data.remote.response.LeaderboardResponse
import com.ndiman.classmath.data.remote.response.LoginResponse
import com.ndiman.classmath.data.remote.response.RegisterResponse
import com.ndiman.classmath.data.remote.response.ResultQuizResponse
import com.ndiman.classmath.data.remote.response.SearchResponse
import com.ndiman.classmath.data.remote.response.UpdateUserResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @POST("users")
    suspend fun register(@Body request: RegisterRequest): RegisterResponse

    @POST("users/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @GET("users/current")
    suspend fun getUser(): GetUserResponse

    @PATCH("users/current")
    suspend fun updateUser(@Body request: UpdateRequest): UpdateUserResponse

    @GET("grades")
    suspend fun getGrade(): GetGradeResponse

    @GET("grades/{idGrade}/tutorials/")
    suspend fun getAllTutorial(
        @Path("idGrade") idGrade: String
    ): GetAllTutorialResponse

    @GET("grades/{idGrade}/tutorials/{idTutorial}/quizzes")
    suspend fun getAllQuizzes(
        @Path("idGrade") idGrade: String,
        @Path("idTutorial") idTutorial: String
    ): GetAllQuizzResponse

    @GET("grades/{idGrade}/tutorials/{idTutorial}/quizzes/{idQuiz}")
    suspend fun getIdQuizzes(
        @Path("idGrade") idGrade: String,
        @Path("idTutorial") idTutorial: String,
        @Path("idQuiz") idQuiz: String
    ): GetIdQuizzResponse

    @POST("username/{username}/quizzes/{idQuiz}/quiz-histories")
    suspend fun resultQuiz(
        @Path("username") username: String,
        @Path("idQuiz") idQuiz: String,
        @Body request: AnswerRequest
    ): ResultQuizResponse

    @GET("tutorials")
    suspend fun search(
        @Query("search") search: String
    ): SearchResponse

    @GET("users/leaderboard")
    suspend fun getLeaderboard(): LeaderboardResponse
}