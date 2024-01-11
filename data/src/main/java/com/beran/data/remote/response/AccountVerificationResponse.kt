package com.beran.data.remote.response

import com.google.gson.annotations.SerializedName

data class AccountVerificationResponse(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class Data(

	@field:SerializedName("roles")
	val roles: List<String?>? = null,

	@field:SerializedName("fullName")
	val fullName: String? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("email")
	val email: String? = null
)
