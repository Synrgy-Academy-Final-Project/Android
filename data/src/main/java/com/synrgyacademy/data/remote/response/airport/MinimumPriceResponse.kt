package com.synrgyacademy.data.remote.response.airport

import com.google.gson.annotations.SerializedName

data class MinimumPriceResponse(

	@field:SerializedName("data")
	val data: List<MinimumPriceItem>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
)

data class MinimumPriceItem(

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("price")
	val price: Int? = null
)
