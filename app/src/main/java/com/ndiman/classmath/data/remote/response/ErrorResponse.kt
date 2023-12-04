package com.ndiman.classmath.data.remote.response

import com.google.gson.annotations.SerializedName

data class ErrorResponse(

	@field:SerializedName("errors")
	val errors: String? = null
)
