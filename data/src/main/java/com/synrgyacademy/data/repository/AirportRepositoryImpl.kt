package com.synrgyacademy.data.repository

import com.synrgyacademy.data.local.room.HistorySearchingDao
import com.synrgyacademy.data.mapper.toAirportDataModel
import com.synrgyacademy.data.mapper.toHistoryEntity
import com.synrgyacademy.data.mapper.toMinimumDataModel
import com.synrgyacademy.data.mapper.toScheduleDataModel
import com.synrgyacademy.data.remote.retrofit.AirportService
import com.synrgyacademy.data.remote.util.SafeApiRequest
import com.synrgyacademy.domain.model.airport.AirportDataModel
import com.synrgyacademy.domain.model.query.GetScheduleFlightQuery
import com.synrgyacademy.domain.model.airport.HistoryDataModel
import com.synrgyacademy.domain.model.airport.HistoryTransactionDataModel
import com.synrgyacademy.domain.model.airport.MinimumDataModel
import com.synrgyacademy.domain.model.airport.PopularPlacesDataModel
import com.synrgyacademy.domain.model.airport.PromotionsDataModel
import com.synrgyacademy.domain.model.airport.ScheduleDataModel
import com.synrgyacademy.domain.model.airport.TransactionDataModel
import com.synrgyacademy.domain.model.airport.UserData
import com.synrgyacademy.domain.model.airport.UserDetailDataModel
import com.synrgyacademy.domain.model.query.MinimumPriceQuery
import com.synrgyacademy.domain.repository.AirportRepository
import com.synrgyacademy.domain.request.TransactionRequest
import com.synrgyacademy.domain.request.UserDetailRequest
import kotlinx.coroutines.flow.first
import okhttp3.ResponseBody
import javax.inject.Inject

class AirportRepositoryImpl @Inject constructor(
    private val api: AirportService,
    private val historySearchingDao: HistorySearchingDao
) : AirportRepository, SafeApiRequest() {

    override suspend fun getAirportList(
        query: String?
    ): List<AirportDataModel> {
        val response = safeApiRequest { api.getAirports(query) }
        return response.data!!.airportList?.map { it }?.toAirportDataModel() ?: emptyList()
    }

    override suspend fun getScheduleFlight(
        getScheduleFlightQuery: GetScheduleFlightQuery
    ): List<ScheduleDataModel> {
        val response = safeApiRequest {
            api.getScheduleFlight(getScheduleFlightQuery.toMap())
        }
        return response.data!!.content?.map { it }?.toScheduleDataModel() ?: emptyList()
    }

    override suspend fun getMinimumPrice(
        minimumPriceQuery: MinimumPriceQuery
    ): List<MinimumDataModel> {
        val response = safeApiRequest {
            api.getMinimumPrice(minimumPriceQuery.toMap())
        }
        return response.data?.map { it }?.toMinimumDataModel() ?: emptyList()
    }

    override suspend fun getAllPromotions(): List<PromotionsDataModel> {
        val response = safeApiRequest { api.getAllPromotion() }
        return response.data?.content?.map { it.toPromotionsDataModel() } ?: emptyList()
    }

    override suspend fun getPromotions(code: String): PromotionsDataModel {
        val response = safeApiRequest { api.getPromotions(code) }
        return response.data!!.toPromotionsDataModel()
    }

    override suspend fun addTransaction(
        token: String,
        transactionRequest: TransactionRequest
    ): TransactionDataModel {
        val response = safeApiRequest {
            api.addTransaction(
                token = "Bearer $token",
                transactionRequest = transactionRequest
            )
        }
        return response.data!!.toTransactionDataModel()
    }

    override suspend fun updateUserData(
        token: String,
        userData: UserDetailRequest
    ): UserDetailDataModel {
        val response = safeApiRequest { api.updateUserDetail("Bearer $token", userData) }
        return response.data!!.toUserDetailDataModel()
    }

    override suspend fun getUserDetailById(id: String): UserDetailDataModel {
        val response = safeApiRequest { api.getUserDetailById(id) }
        return response.data!!.toUserDetailDataModel()
    }

    override suspend fun getUserDetailByToken(token: String): UserData {
        val response = safeApiRequest {
            api.getUserByToken(
                token = "Bearer $token"
            )
        }

        return response.data!!.toUserData()
    }

    override suspend fun getEticketAttachFile(token: String, idTransaksi: String): ResponseBody {
        return safeApiRequest { api.getEticketAttachFile("Bearer $token", idTransaksi) }
    }

    override suspend fun getEticketByEmail(token: String, idTransaksi: String): String {
        val response = safeApiRequest { api.getEticketByEmail("Bearer $token", idTransaksi) }
        return response.message.toString()
    }

    override suspend fun getHistoryTransaction(token: String): List<HistoryTransactionDataModel> {
        val response = safeApiRequest { api.getTransactionHistory("Bearer $token") }
        return response.data?.content?.map { it.toHistoryTransactionDataModel() } ?: emptyList()
    }

    override suspend fun getHistoryTransactionById(
        token: String,
        idTransaction: String
    ): HistoryTransactionDataModel {
        val response = safeApiRequest { api.getTransactionHistoryById("Bearer $token", idTransaction) }
        return response.data!!.map { it.toHistoryTransactionDataModel() }.first()
    }

    override suspend fun insertHistorySearching(historyDataModel: HistoryDataModel) =
        try {
            historySearchingDao.insertHistorySearching(historyDataModel.toHistoryEntity())
        } catch (e: Exception) {
            throw e
        }

    override suspend fun deleteAllHistorySearching() =
        try {
            historySearchingDao.deleteAllHistorySearching()
        } catch (e: Exception) {
            throw e
        }

    override suspend fun getAllHistorySearching(): List<HistoryDataModel> =
        try {
            historySearchingDao.getAllHistorySearching().first().map { it.toHistoryDataModel() }
        } catch (e: Exception) {
            throw e
        }

    override fun getHistorySearchingById(id: Int): HistoryDataModel =
        try {
            historySearchingDao.getHistorySearchingById(id).toHistoryDataModel()
        } catch (e: Exception) {
            throw e
        }

    override suspend fun getPopularPlaces(): List<PopularPlacesDataModel> {
        return safeApiRequest { api.getPopularPlaces() }
    }
}