package com.synrgyacademy.data.remote.retrofit

import com.synrgyacademy.data.remote.response.tourism.TourismLikeResponse
import com.synrgyacademy.data.remote.response.tourism.TourismResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path
import retrofit2.http.Query

interface TourismService {

    @GET("wisata")
    suspend fun getTourismPlaces(): Response<TourismResponse>

    @GET("wisata/search")
    suspend fun getTourismPlacesByName(
        @Query("q") name: String
    ): Response<TourismResponse>

    @PATCH("wisata/add/like/{id}")
    suspend fun addLikeTourism(
        @Path("id") id: String
    ): Response<TourismLikeResponse>
}