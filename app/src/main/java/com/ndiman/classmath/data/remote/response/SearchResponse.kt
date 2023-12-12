package com.ndiman.classmath.data.remote.response

import com.google.gson.annotations.SerializedName

data class SearchResponse(

	@field:SerializedName("data")
	val data: DataSearch? = null
)

data class QuestionsItemSearch(

	@field:SerializedName("answer")
	val answer: String? = null,

	@field:SerializedName("question_file")
	val questionFile: Any? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("id_quiz")
	val idQuiz: String? = null,

	@field:SerializedName("choices")
	val choices: List<String?>? = null,

	@field:SerializedName("content")
	val content: String? = null
)

data class TutorialItem(

	@field:SerializedName("chapter")
	val chapter: Int? = null,

	@field:SerializedName("tutorial_file")
	val tutorialFile: String? = null,

	@field:SerializedName("tutorial_image")
	val tutorialImage: String? = null,

	@field:SerializedName("grade")
	val grade: Grade? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("title")
	val title: String? = null
)

data class QuizItem(

	@field:SerializedName("questions")
	val questions: List<QuestionsItemSearch> = emptyList(),

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("title")
	val title: String? = null
)

data class DataSearch(

	@field:SerializedName("quiz")
	val quiz: List<QuizItem> = emptyList(),

	@field:SerializedName("tutorial")
	val tutorial: List<TutorialItem> = emptyList()
)

data class GradeSearch(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("grade_image")
	val gradeImage: String? = null
)
