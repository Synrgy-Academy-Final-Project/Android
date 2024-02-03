package com.synrgyacademy.domain.model.airport

data class AirplaneDataModel (
    val id: Int,

    val fromCity: String,

    val toCity: String,

    val date: String,

    val passengerNumber: Int,

    val adultNumber: Int,

    val childNumber: Int,

    val infantNumber: Int,

    val airplaneClass: String,
)