package com.synrgyacademy.data.remote.retrofit

import com.synrgyacademy.domain.request.UserDetailRequest
import com.synrgyacademy.data.remote.response.airport.AirportResponse
import com.synrgyacademy.data.remote.response.airport.AllPromotionResponse
import com.synrgyacademy.data.remote.response.airport.EticketResponse
import com.synrgyacademy.data.remote.response.airport.GetDetailHistoryTransactionResponse
import com.synrgyacademy.data.remote.response.airport.GetHistoryTransactionResponse
import com.synrgyacademy.data.remote.response.airport.GetUserByTokenResponse
import com.synrgyacademy.data.remote.response.airport.MinimumPriceResponse
import com.synrgyacademy.data.remote.response.airport.PromotionResponse
import com.synrgyacademy.data.remote.response.airport.ScheduleResponse
import com.synrgyacademy.data.remote.response.airport.TransactionResponse
import com.synrgyacademy.data.remote.response.airport.UserDetailResponse
import com.synrgyacademy.domain.model.airport.PopularPlacesDataModel
import com.synrgyacademy.domain.request.TransactionRequest
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface AirportService {
    @GET("airports")
    suspend fun getAirports(
        @Query("query") query: String? = null,
    ): Response<AirportResponse>

    @GET("scheduleflight")
    suspend fun getScheduleFlight(
        @QueryMap getScheduleFlightQuery: Map<String, String>,
    ): Response<ScheduleResponse>

    @GET("airplane/minimum-price")
    suspend fun getMinimumPrice(
        @QueryMap minimumPriceQuery: Map<String, String>,
    ): Response<MinimumPriceResponse>

    suspend fun getPopularPlaces(): Response<List<PopularPlacesDataModel>>

    @GET("promotions")
    suspend fun getAllPromotion() : Response<AllPromotionResponse>

    @GET("promotions/{code}")
    suspend fun getPromotions(
        @Path("code") code: String,
    ): Response<PromotionResponse>

    @POST("transaction/midtrans")
    suspend fun addTransaction(
        @Header("Authorization") token: String,
        @Body transactionRequest: TransactionRequest,
    ): Response<TransactionResponse>

    @PUT("user-detail")
    suspend fun updateUserDetail(
        @Header("Authorization") token: String,
        @Body userDetail: UserDetailRequest,
    ): Response<UserDetailResponse>

    @GET("user-detail/{id}")
    suspend fun getUserDetailById(
        @Path("id") id: String,
    ): Response<UserDetailResponse>

    @GET("user-detail/logged-in-user")
    suspend fun getUserByToken(
        @Header("Authorization") token: String,
    ): Response<GetUserByTokenResponse>

    @POST("report/eticket-link/{id}")
    suspend fun getEticketAttachFile(
        @Header("Authorization") token: String,
        @Path("id") idTransaction: String,
    ): Response<ResponseBody>

    @POST("report/eticket/{id}")
    suspend fun getEticketByEmail(
        @Header("Authorization") token: String,
        @Path("id") idTransaction: String,
    ): Response<EticketResponse>

    @GET("transaction/history")
    suspend fun getTransactionHistory(
        @Header("Authorization") token: String,
    ): Response<GetHistoryTransactionResponse>

    @GET("transaction/history/detail/{id}")
    suspend fun getTransactionHistoryById(
        @Header("Authorization") token: String,
        @Path("id") idTransaction: String,
    ): Response<GetDetailHistoryTransactionResponse>

}