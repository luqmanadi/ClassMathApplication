package com.ndiman.classmath.data.remote.response

import com.google.gson.annotations.SerializedName

data class ResultQuizResponse(

	@field:SerializedName("data")
	val data: DataResultQuiz? = null
)

data class DataResultQuiz(

	@field:SerializedName("score")
	val score: Int? = null,

	@field:SerializedName("correct")
	val correct: Int? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("id_quiz")
	val idQuiz: String? = null,

	@field:SerializedName("not_answered")
	val notAnswered: Int? = null,

	@field:SerializedName("wrong")
	val wrong: Int? = null,

	@field:SerializedName("username")
	val username: String? = null
)
