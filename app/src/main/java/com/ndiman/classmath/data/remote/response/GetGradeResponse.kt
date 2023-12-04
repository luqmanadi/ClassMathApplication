package com.ndiman.classmath.data.remote.response

import com.google.gson.annotations.SerializedName

data class GetGradeResponse(

	@field:SerializedName("data")
	val data: List<DataItemGrade> = emptyList()
)

data class DataItemGrade(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("grade_image")
	val gradeImage: String? = null
)
