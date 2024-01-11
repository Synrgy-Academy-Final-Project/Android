package com.beran.data.remote.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("data")
	val data: AuthData? = null,

	@field:SerializedName("status")
	val status: String? = null
)
