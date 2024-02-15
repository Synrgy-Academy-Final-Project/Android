package com.synrgyacademy.data.remote.response

import com.google.gson.annotations.SerializedName
import com.synrgyacademy.domain.model.airport.HistoryTransactionDataModel

data class GetHistoryTransactionResponse(

	@field:SerializedName("data")
	val data: HistoryDetail? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
)

data class HistoryDetail(

	@field:SerializedName("number")
	val number: Int? = null,

	@field:SerializedName("last")
	val last: Boolean? = null,

	@field:SerializedName("size")
	val size: Int? = null,

	@field:SerializedName("numberOfElements")
	val numberOfElements: Int? = null,

	@field:SerializedName("totalPages")
	val totalPages: Int? = null,

	@field:SerializedName("pageable")
	val pageable: Pageable? = null,

	@field:SerializedName("sort")
	val sort: Sort? = null,

	@field:SerializedName("content")
	val content: List<HistoryItem>? = null,

	@field:SerializedName("first")
	val first: Boolean? = null,

	@field:SerializedName("totalElements")
	val totalElements: Int? = null,

	@field:SerializedName("empty")
	val empty: Boolean? = null
)

data class HistoryItem(
	@field:SerializedName("orderId")
	val orderId: String? = null,

	@field:SerializedName("urlCompany")
	val urlCompany: String? = null,

	@field:SerializedName("departureCode")
	val departureCode: String? = null,

	@field:SerializedName("departureTime")
	val departureTime: String? = null,

	@field:SerializedName("departureDate")
	val departureDate: String? = null,

	@field:SerializedName("arrivalCode")
	val arrivalCode: String? = null,

	@field:SerializedName("arrivalTime")
	val arrivalTime: String? = null,

	@field:SerializedName("arrivalDate")
	val arrivalDate: String? = null,

	@field:SerializedName("durationAirplane")
	val durationAirplane: String? = null,

	@field:SerializedName("departureCityCode")
	val departureCityCode: String? = null,

	@field:SerializedName("departureAirportName")
	val departureAirportName: String? = null,

	@field:SerializedName("departureCountry")
	val departureCountry: String? = null,

	@field:SerializedName("arrivalCityCode")
	val arrivalCityCode: String? = null,

	@field:SerializedName("arrivalAirportName")
	val arrivalAirportName: String? = null,

	@field:SerializedName("arrivalCountry")
	val arrivalCountry: String? = null,

	@field:SerializedName("oderCode")
	val oderCode: String? = null,

	@field:SerializedName("paymentMethod")
	val paymentMethod: String? = null,

	@field:SerializedName("transactionStatus")
	val transactionStatus: String? = null,

	@field:SerializedName("totalPrice")
	val totalPrice: Int? = null
) {
	fun toHistoryTransactionDataModel(): HistoryTransactionDataModel {
		return HistoryTransactionDataModel(
			orderId = orderId,
			urlCompany = urlCompany,
			departureCode = departureCode,
			departureTime = departureTime,
			departureDate = departureDate,
			arrivalCode = arrivalCode,
			arrivalTime = arrivalTime,
			arrivalDate = arrivalDate,
			durationAirplane = durationAirplane,
			departureCityCode = departureCityCode,
			departureAirportName = departureAirportName,
			departureCountry = departureCountry,
			arrivalCityCode = arrivalCityCode,
			arrivalAirportName = arrivalAirportName,
			arrivalCountry = arrivalCountry,
			oderCode = oderCode,
			paymentMethod = paymentMethod,
			transactionStatus = transactionStatus,
			totalPrice = totalPrice
		)
	}
}