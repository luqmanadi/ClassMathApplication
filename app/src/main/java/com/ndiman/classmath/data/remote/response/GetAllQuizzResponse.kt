package com.ndiman.classmath.data.remote.response

import com.google.gson.annotations.SerializedName

data class GetAllQuizzResponse(

	@field:SerializedName("data")
	val data: List<DataItem> = emptyList()
)

data class DataItem(

	@field:SerializedName("questions")
	val questions: List<QuestionsItemAllQuizz> = emptyList(),

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("title")
	val title: String? = null
)

data class QuestionsItemAllQuizz(

	@field:SerializedName("question_file")
	val questionFile: Any? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("choices")
	val choices: List<String> = emptyList(),

	@field:SerializedName("content")
	val content: String? = null
)
