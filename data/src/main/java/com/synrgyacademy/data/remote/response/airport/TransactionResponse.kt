package com.synrgyacademy.data.remote.response.airport

import com.google.gson.annotations.SerializedName
import com.synrgyacademy.domain.model.airport.TransactionDataModel


data class TransactionResponse(

	@field:SerializedName("data")
	val data: TransactionData? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null,

	@field:SerializedName("error")
	val error: String? = null
)

data class TransactionData(

	@field:SerializedName("redirectUrl")
	val redirectUrl: String? = null,

	@field:SerializedName("orderId")
	val orderId: String? = null,

	@field:SerializedName("token")
	val token: String? = null
) {
	fun toTransactionDataModel() = TransactionDataModel(
		redirectUrl = redirectUrl.orEmpty(),
		orderId = orderId.orEmpty(),
		token = token.orEmpty()
	)

}