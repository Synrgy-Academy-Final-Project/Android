package com.synrgyacademy.domain.model.query

data class MinimumPriceQuery(
    val fromAirportCode: String,
    val toAirportCode: String,
    val departureDate: String,
    val airplaneClass: String,
) {
    fun toMap(): Map<String, String> {
        return mapOf(
            "fromAirportCode" to fromAirportCode,
            "toAirportCode" to toAirportCode,
            "departureDate" to departureDate,
            "airplaneClass" to airplaneClass,
        )
    }
}