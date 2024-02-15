package com.synrgyacademy.data.repository

import com.synrgyacademy.data.local.model.PassengerEntity
import com.synrgyacademy.data.local.model.PassengerEntity.Companion.toPassengerDataModelList
import com.synrgyacademy.data.local.room.PassengerDao
import com.synrgyacademy.domain.model.passenger.PassengerDataModel
import com.synrgyacademy.domain.repository.PassengerRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class PassengerRepositoryImpl @Inject constructor(
    private val passengerDao: PassengerDao
) : PassengerRepository {

    override suspend fun insertPassenger(passengerDataModel: PassengerDataModel) =
        try {
            passengerDao.insertPassenger(PassengerEntity.from(passengerDataModel))
        } catch (e: Exception) {
            throw e
        }

    override suspend fun deleteAllPassenger() =
        try {
            passengerDao.deleteAllPassenger()
        } catch (e: Exception) {
            throw e
        }

    override suspend fun updatePassenger(passengerDataModel: PassengerDataModel) =
        try {
            passengerDao.updatePassenger(PassengerEntity.from(passengerDataModel))
        } catch (e: Exception) {
            throw e
        }

    override fun getCountPassenger(): Int =
        try {
            passengerDao.getCountPassenger()
        } catch (e: Exception) {
            throw e
        }

    override suspend fun getAllPassenger(): List<PassengerDataModel> =
        try {
            passengerDao.getAllPassenger().first().toPassengerDataModelList()
        } catch (e: Exception) {
            throw e
        }

    override fun getPassengerById(id: Int): PassengerDataModel =
        try {
            passengerDao.getPassengerById(id).toPassengerDataModel()
        } catch (e: Exception) {
            throw e
        }

    override fun getPassengerByName(name: String): PassengerDataModel =
        try {
            passengerDao.getPassengerByName(name).toPassengerDataModel()
        } catch (e: Exception) {
            throw e
        }

    override fun getPassengerByType(type: String): PassengerDataModel =
        try {
            passengerDao.getPassengerByType(type).toPassengerDataModel()
        } catch (e: Exception) {
            throw e
        }
}