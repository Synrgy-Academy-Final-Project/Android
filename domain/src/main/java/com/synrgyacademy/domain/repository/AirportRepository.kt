package com.synrgyacademy.domain.repository

import com.synrgyacademy.domain.model.airport.AirportDataModel
import com.synrgyacademy.domain.model.airport.HistoryDataModel
import com.synrgyacademy.domain.model.airport.HistoryTransactionDataModel
import com.synrgyacademy.domain.model.airport.MinimumDataModel
import com.synrgyacademy.domain.model.airport.PromotionsDataModel
import com.synrgyacademy.domain.model.airport.ScheduleDataModel
import com.synrgyacademy.domain.model.airport.TransactionDataModel
import com.synrgyacademy.domain.model.airport.UserData
import com.synrgyacademy.domain.model.airport.UserDetailDataModel
import com.synrgyacademy.domain.model.query.GetScheduleFlightQuery
import com.synrgyacademy.domain.model.query.MinimumPriceQuery
import com.synrgyacademy.domain.request.TransactionRequest
import com.synrgyacademy.domain.request.UserDetailRequest
import okhttp3.ResponseBody

interface AirportRepository {
    // Bellow is an API Call
    suspend fun getAirportList(
        query: String? = null
    ): List<AirportDataModel>

    suspend fun getScheduleFlight(
        getScheduleFlightQuery: GetScheduleFlightQuery
    ): List<ScheduleDataModel>

    suspend fun getMinimumPrice(
        minimumPriceQuery: MinimumPriceQuery
    ): List<MinimumDataModel>

    suspend fun getAllPromotions(): List<PromotionsDataModel>

    suspend fun getPromotions(
        code: String,
    ): PromotionsDataModel

    suspend fun addTransaction(
        token: String,
        transactionRequest: TransactionRequest
    ): TransactionDataModel

    suspend fun updateUserData(token: String, userData: UserDetailRequest): UserDetailDataModel

    suspend fun getUserDetailById(
        id: String
    ): UserDetailDataModel

    suspend fun getUserDetailByToken(
        token: String
    ): UserData

    suspend fun getEticketAttachFile(
        token: String,
        idTransaksi: String
    ): ResponseBody

    suspend fun getEticketByEmail(
        token: String,
        idTransaksi: String
    ): String

    suspend fun getHistoryTransaction(
        token: String
    ): List<HistoryTransactionDataModel>

    suspend fun getHistoryTransactionById(
        token: String,
        idTransaction: String
    ): HistoryTransactionDataModel

    // Bellow is a Room Database Call
    suspend fun insertHistorySearching(historyDataModel: HistoryDataModel)

    suspend fun deleteAllHistorySearching()

    suspend fun getAllHistorySearching(): List<HistoryDataModel>

    fun getHistorySearchingById(id: Int): HistoryDataModel

}