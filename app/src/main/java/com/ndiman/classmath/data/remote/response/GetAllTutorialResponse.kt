package com.ndiman.classmath.data.remote.response

import com.google.gson.annotations.SerializedName

data class GetAllTutorialResponse(

	@field:SerializedName("data")
	val data: List<DataItemTutorial> = emptyList()
)

data class DataItemTutorial(

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

data class Grade(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("grade_image")
	val gradeImage: String? = null
)
