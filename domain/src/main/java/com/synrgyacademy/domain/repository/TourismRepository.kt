package com.synrgyacademy.domain.repository

import com.synrgyacademy.domain.model.tourism.TourismDataModel

interface TourismRepository {

    suspend fun getTourismPlaces(): List<TourismDataModel>

    suspend fun getTourismPlacesByName(name: String): List<TourismDataModel>

    suspend fun addLikeTourism(id: String): TourismDataModel
}