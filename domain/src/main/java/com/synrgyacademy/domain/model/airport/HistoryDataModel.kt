package com.synrgyacademy.domain.model.airport

import com.synrgyacademy.domain.model.passenger.PassengerTotal

data class HistoryDataModel (
    val id: Int,

    val departureData: AirportDataModel,

    val arrivalData: AirportDataModel,

    val departureDate: String,

    val searchingDate: String,

    val passenger: PassengerTotal,

    val airplaneClass: String
)