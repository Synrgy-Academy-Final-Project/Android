package com.synrgyacademy.domain.model.query

data class GetScheduleFlightQuery(
    val departureCode: String,
    val arrivalCode: String,
    val departureDate: String,
    val airplaneClass: String,
    val fromPrice: String? = null,
    val toPrice: String? = null,
    val departureTime: String? = null,
    val baggage: String? = null,
    val entertainment: String? = null,
    val meals: String? = null,
    val usb: String? = null,
    val wifi: String? = null,
    val refund: String? = null,
    val reschedule: String? = null
) {
    fun toMap(): Map<String, String> {
        return mapOf(
            "departureCode" to departureCode,
            "arrivalCode" to arrivalCode,
            "departureDate" to departureDate,
            "airplaneClass" to airplaneClass,
            "fromPrice" to fromPrice.orEmpty(),
            "toPrice" to toPrice.orEmpty(),
            "departureTime" to departureTime.orEmpty(),
            "baggage" to baggage.orEmpty(),
            "entertainment" to entertainment.orEmpty(),
            "meals" to meals.orEmpty(),
            "usb" to usb.orEmpty(),
            "wifi" to wifi.orEmpty(),
            "refund" to refund.orEmpty(),
            "reschedule" to reschedule.orEmpty()
        )
    }
}