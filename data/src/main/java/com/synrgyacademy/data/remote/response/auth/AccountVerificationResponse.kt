package com.synrgyacademy.data.remote.response.auth

import com.google.gson.annotations.SerializedName

data class AccountVerificationResponse(

	@field:SerializedName("data")
	val data: AuthData? = null,

	@field:SerializedName("status")
	val status: String? = null
)