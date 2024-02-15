package com.synrgyacademy.data.remote.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("data")
	val data: LoginData? = null,

	@field:SerializedName("status")
	val status: Int? = null
)

data class LoginData(

	@field:SerializedName("roles")
	val roles: List<String>? = null,

	@field:SerializedName("fullName")
	val fullName: String? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("token")
	val token: String? = null
)
