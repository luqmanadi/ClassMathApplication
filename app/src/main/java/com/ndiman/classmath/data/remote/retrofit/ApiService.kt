package com.ndiman.classmath.data.remote.retrofit

import com.ndiman.classmath.data.remote.response.GetUserResponse
import com.ndiman.classmath.data.remote.response.LoginResponse
import com.ndiman.classmath.data.remote.response.RegisterResponse
import com.ndiman.classmath.data.remote.response.UpdateUserResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST

interface ApiService {
    @POST("users")
    suspend fun register(@Body request: RegisterRequest): RegisterResponse

    @POST("users/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @GET("users/current")
    suspend fun getUser(): GetUserResponse

    @PATCH("users/current")
    suspend fun updateUser(@Body request: UpdateRequest): UpdateUserResponse
}