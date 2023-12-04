package com.ndiman.classmath.data.remote.response

import com.google.gson.annotations.SerializedName

data class GetUserResponse(

	@field:SerializedName("data")
	val data: DataUser? = null
)

data class DataUser(

	@field:SerializedName("is_admin")
	val isAdmin: Boolean? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("total_score")
	val totalScore: Int? = null,

	@field:SerializedName("username")
	val username: String? = null,

	@field:SerializedName("token")
	val token: String? = null
)
