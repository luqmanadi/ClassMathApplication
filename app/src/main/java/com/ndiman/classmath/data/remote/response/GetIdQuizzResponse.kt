package com.ndiman.classmath.data.remote.response

import com.google.gson.annotations.SerializedName

data class GetIdQuizzResponse(

	@field:SerializedName("data")
	val data: Data? = null
)

data class QuestionsItem(

	@field:SerializedName("question_file")
	val questionFile: Any? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("choices")
	val choices: List<String?>? = null,

	@field:SerializedName("content")
	val content: String? = null
)

data class Data(

	@field:SerializedName("questions")
	val questions: List<QuestionsItem> = emptyList(),

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("title")
	val title: String? = null
)
