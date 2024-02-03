package com.synrgyacademy.data.remote.retrofit

import com.synrgyacademy.data.remote.response.AirportResponse
import com.synrgyacademy.data.remote.response.MinimumPriceResponse
import com.synrgyacademy.data.remote.response.ScheduleResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AirportService {
    @GET("airports")
    suspend fun getAirports(
        @Query("query") query: String? = null,
    ): Response<AirportResponse>

    @GET("scheduleflight")
    suspend fun getScheduleFlight(
        @Query("departureCode") departureCode: String,
        @Query("arrivalCode") arrivalCode: String,
        @Query("departureDate") departureDate: String,
        @Query("airplaneClass") airplaneClass: String,
    ): Response<ScheduleResponse>

    @GET("airplane/minimum-price")
    suspend fun getMinimumPrice(
        @Query("fromAirportCode") fromAirportCode: String,
        @Query("toAirportCode") toAirportCode: String,
        @Query("departureDate") departureDate: String,
    ): Response<MinimumPriceResponse>
}