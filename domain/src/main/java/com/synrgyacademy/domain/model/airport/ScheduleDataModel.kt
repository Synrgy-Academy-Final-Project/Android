package com.synrgyacademy.domain.model.airport

data class ScheduleDataModel (
    val companyName: String,
    val urlLogo: String,
    val airplaneId: String,
    val airplaneName: String,
    val airplaneCode: String,
    val airplaneClassId: String,
    val airplaneClass: String,
    val capacity: Int,
    val airplaneServices: ScheduleServicesItem,
    val airplaneFlightTimeId: String,
    val flightTime: String,
    val departureCode: String,
    val arrivalCode: String,
    val departureTime: String,
    val arrivalTime: String,
    val totalPrice: Int,
)

data class ScheduleServicesItem(
    val baggage: Int,
    val cabinBaggage: Int,
    val meals: Boolean,
    val travelInsurance: Boolean,
    val inflightEntertainment: Boolean,
    val electricSocket: Boolean,
    val wifi: Boolean,
    val reschedule: Boolean,
    val refund: Int,
)