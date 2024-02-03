package com.synrgyacademy.domain.repository

import com.synrgyacademy.domain.model.airport.AirplaneDataModel
import com.synrgyacademy.domain.model.airport.AirportDataModel
import com.synrgyacademy.domain.model.airport.MinimumDataModel
import com.synrgyacademy.domain.model.airport.ScheduleDataModel

interface AirportRepository {
    // Bellow is an API Call
    suspend fun getAirportList(
        query: String? = null
    ): List<AirportDataModel>

    suspend fun getScheduleFlight(
        departureCode: String,
        arrivalCode: String,
        departureDate: String,
        airplaneClass: String,
    ): List<ScheduleDataModel>

    suspend fun getMinimumPrice(
        fromAirportCode: String,
        toAirportCode: String,
        departureDate: String,
    ): List<MinimumDataModel>

    // Bellow is a Room Database Call
    suspend fun insertHistorySearching(airplaneDataModel: AirplaneDataModel)

    suspend fun deleteAllHistorySearching()

    suspend fun getAllHistorySearching(): List<AirplaneDataModel>

    fun getHistorySearchingById(id: Int): AirplaneDataModel
}