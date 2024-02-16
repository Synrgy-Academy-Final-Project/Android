package com.synrgyacademy.data.repository

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.synrgyacademy.data.local.room.TourismDao
import com.synrgyacademy.data.remote.retrofit.TourismService
import com.synrgyacademy.data.remote.util.SafeApiRequest
import com.synrgyacademy.domain.model.tourism.TourismDataModel
import com.synrgyacademy.domain.repository.TourismRepository
import javax.inject.Inject

class TourismRepositoryImpl @Inject constructor(
    private val tourismService: TourismService,
    private val tourismDao: TourismDao,
    private val context: Context
) : TourismRepository, SafeApiRequest() {

    override suspend fun getTourismPlaces(): List<TourismDataModel> {
        return if (isNetworkAvailable()) {
            val response = safeApiRequest { tourismService.getTourismPlaces() }
            val data = response.data.map { it.toTourismDataModel() }
            tourismDao.insertTourism(response.data.map { it.toTourismEntity() })
            data
        } else {
            val data = tourismDao.getTourism()
            data.map { it.toTourismDataModel() }
        }
    }

    override suspend fun getTourismPlacesByName(name: String): List<TourismDataModel> {
        return if (isNetworkAvailable()) {
            val response = safeApiRequest { tourismService.getTourismPlacesByName(name) }
            val data = response.data.map { it.toTourismDataModel() }
            tourismDao.insertTourism(response.data.map { it.toTourismEntity() })
            data
        } else {
            val data = tourismDao.searchTourism("%$name%")
            data.map { it.toTourismDataModel() }
        }
    }

    override suspend fun addLikeTourism(id: String): TourismDataModel {
        val response = safeApiRequest { tourismService.addLikeTourism(id) }
        return response.data.toTourismDataModel()
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
        return activeNetwork.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

}