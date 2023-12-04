package com.ndiman.classmath.data.remote.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("data")
	val data: DataLogin
)

data class DataLogin(

	@field:SerializedName("token")
	val token: String
)
