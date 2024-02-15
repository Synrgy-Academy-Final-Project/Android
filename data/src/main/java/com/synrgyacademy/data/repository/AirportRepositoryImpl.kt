package com.synrgyacademy.data.repository

import com.synrgyacademy.data.local.room.AirplaneDao
import com.synrgyacademy.data.mapper.toAirplaneDataModel
import com.synrgyacademy.data.mapper.toAirplaneEntity
import com.synrgyacademy.data.mapper.toAirportDataModel
import com.synrgyacademy.data.mapper.toMinimumDataModel
import com.synrgyacademy.data.mapper.toScheduleDataModel
import com.synrgyacademy.data.remote.retrofit.AirportService
import com.synrgyacademy.data.remote.util.SafeApiRequest
import com.synrgyacademy.domain.model.airport.AirplaneDataModel
import com.synrgyacademy.domain.model.airport.AirportDataModel
import com.synrgyacademy.domain.model.airport.MinimumDataModel
import com.synrgyacademy.domain.model.airport.ScheduleDataModel
import com.synrgyacademy.domain.repository.AirportRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class AirportRepositoryImpl @Inject constructor(
    private val api: AirportService,
    private val airplaneDao: AirplaneDao
) : AirportRepository, SafeApiRequest() {

    override suspend fun getAirportList(
        query: String?
    ): List<AirportDataModel> {
        val response = safeApiRequest { api.getAirports(query) }
        return response.data!!.airportList?.map { it }?.toAirportDataModel() ?: emptyList()
    }

    override suspend fun getScheduleFlight(
        departureCode: String,
        arrivalCode: String,
        departureDate: String,
        airplaneClass: String
    ): List<ScheduleDataModel> {
        val response = safeApiRequest {
            api.getScheduleFlight(
                departureCode = departureCode,
                arrivalCode = arrivalCode,
                departureDate = departureDate,
                airplaneClass = airplaneClass
            )
        }
        return response.data!!.content?.map { it }?.toScheduleDataModel() ?: emptyList()
    }

    override suspend fun getMinimumPrice(
        fromAirportCode: String,
        toAirportCode: String,
        departureDate: String
    ): List<MinimumDataModel> {
        val response = safeApiRequest {
            api.getMinimumPrice(
                fromAirportCode = fromAirportCode,
                toAirportCode = toAirportCode,
                departureDate = departureDate
            )
        }
        return response.data?.map { it }?.toMinimumDataModel() ?: emptyList()
    }

    override suspend fun insertHistorySearching(airplaneDataModel: AirplaneDataModel) =
        try {
            airplaneDao.insertHistorySearching(airplaneDataModel.toAirplaneEntity())
        } catch (e: Exception) {
            throw e
        }

    override suspend fun deleteAllHistorySearching() =
        try {
            airplaneDao.deleteAllHistorySearching()
        } catch (e: Exception) {
            throw e
        }

    override suspend fun getAllHistorySearching(): List<AirplaneDataModel> =
        try {
            airplaneDao.getAllHistorySearching().first().toAirplaneDataModel()
        } catch (e: Exception) {
            throw e
        }

    override fun getHistorySearchingById(id: Int): AirplaneDataModel =
        try {
            airplaneDao.getHistorySearchingById(id).toAirplaneDataModel()
        } catch (e: Exception) {
            throw e
        }
}