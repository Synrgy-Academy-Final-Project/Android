package com.synrgyacademy.data.remote.response

import com.google.gson.annotations.SerializedName

data class ScheduleResponse(

    @field:SerializedName("data")
    val data: ScheduleDetailData? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("status")
    val status: Int? = null,
)

data class ScheduleDetailData(
    @field:SerializedName("content")
    val content: List<ScheduleData>? = null,

    @field:SerializedName("pageable")
    val pageable: Pageable? = null,

    @field:SerializedName("last")
    val last: Boolean? = null,

    @field:SerializedName("totalPages")
    val totalPages: Int? = null,

    @field:SerializedName("totalElements")
    val totalElements: Int? = null,

    @field:SerializedName("size")
    val size: Int? = null,

    @field:SerializedName("number")
    val number: Int? = null,

    @field:SerializedName("sort")
    val sort: Sort? = null,

    @field:SerializedName("first")
    val first: Boolean? = null,

    @field:SerializedName("numberOfElements")
    val numberOfElements: Int? = null,

    @field:SerializedName("empty")
    val empty: Boolean? = null
)

data class ScheduleData(

    @field:SerializedName("companyName")
    val companyName: String? = null,

    @field:SerializedName("urlLogo")
    val urlLogo: String? = null,

    @field:SerializedName("airplaneId")
    val airplaneId: String? = null,

    @field:SerializedName("airplaneName")
    val airplaneName: String? = null,

    @field:SerializedName("airplaneCode")
    val airplaneCode: String? = null,

    @field:SerializedName("airplaneClassId")
    val airplaneClassId: String? = null,

    @field:SerializedName("airplaneClass")
    val airplaneClass: String? = null,

    @field:SerializedName("capacity")
    val capacity: Int? = null,

    @field:SerializedName("airplaneServices")
    val airplaneServices: AirplaneServicesItem,

    @field:SerializedName("airplaneFlightTimeId")
    val airplaneFlightTimeId: String? = null,

    @field:SerializedName("flightTime")
    val flightTime: String? = null,

    @field:SerializedName("departureCode")
    val departureCode: String? = null,

    @field:SerializedName("departureCityCode")
    val departureCityCode: String? = null,

    @field:SerializedName("departureNameAirport")
    val departureNameAirport: String? = null,

    @field:SerializedName("arrivalCode")
    val arrivalCode: String? = null,

    @field:SerializedName("arrivalCityCode")
    val arrivalCityCode: String? = null,

    @field:SerializedName("arrivalNameAirport")
    val arrivalNameAirport: String? = null,

    @field:SerializedName("departureTime")
    val departureTime: String? = null,

    @field:SerializedName("arrivalTime")
    val arrivalTime: String? = null,

    @field:SerializedName("totalPrice")
    val totalPrice: Int? = null,
)

data class AirplaneServicesItem(

    @field:SerializedName("baggage")
    val baggage: Int? = null,

    @field:SerializedName("cabinBaggage")
    val cabinBaggage: Int? = null,

    @field:SerializedName("meals")
    val meals: Boolean? = null,

    @field:SerializedName("travelInsurance")
    val travelInsurance: Boolean? = null,

    @field:SerializedName("inflightEntertainment")
    val inflightEntertainment: Boolean? = null,

    @field:SerializedName("electricSocket")
    val electricSocket: Boolean? = null,

    @field:SerializedName("wifi")
    val wifi: Boolean? = null,

    @field:SerializedName("reschedule")
    val reschedule: Boolean? = null,

    @field:SerializedName("refund")
    val refund: Int? = null,
)