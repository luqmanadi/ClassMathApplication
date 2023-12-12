package com.ndiman.classmath.data.remote.response

import com.google.gson.annotations.SerializedName

data class LeaderboardResponse(

	@field:SerializedName("data")
	val data: List<DataItemLeaderboard> = emptyList()
)

data class DataItemLeaderboard(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("total_score")
	val totalScore: Int? = null,

	@field:SerializedName("username")
	val username: String? = null
)
