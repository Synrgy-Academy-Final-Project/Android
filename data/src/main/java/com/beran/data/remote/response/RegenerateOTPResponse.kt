package com.beran.data.remote.response

import com.google.gson.annotations.SerializedName

data class RegenerateOTPResponse(

	@field:SerializedName("data")
	val data: DataOTP? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class DataOTP(

	@field:SerializedName("message")
	val message: String? = null
)
