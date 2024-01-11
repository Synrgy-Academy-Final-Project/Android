package com.beran.data.remote.request

import com.google.gson.annotations.SerializedName

data class RegisterRequest(

	@field:SerializedName("password")
	val password: String,

	@field:SerializedName("role")
	val role: String = "ROLE_USER",

	@field:SerializedName("fullName")
	val fullName: String,

	@field:SerializedName("email")
	val email: String
)
