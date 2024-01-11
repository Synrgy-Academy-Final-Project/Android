package com.beran.data.remote.request

import com.google.gson.annotations.SerializedName

data class OTPRequest(

	@field:SerializedName("otp")
	val otp: String
)
