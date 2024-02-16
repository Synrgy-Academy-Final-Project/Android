package com.synrgyacademy.domain.repository

import com.synrgyacademy.domain.model.passenger.PassengerDataModel

interface PassengerRepository {

    suspend fun insertPassenger(passengerDataModel: PassengerDataModel)

    suspend fun deleteAllPassenger()

    suspend fun updatePassenger(passengerDataModel: PassengerDataModel)

    fun getCountPassenger() : Int

    suspend fun getAllPassenger() : List<PassengerDataModel>

    fun getPassengerById(id: Int) : PassengerDataModel

    fun getPassengerByName(name: String) : PassengerDataModel

    fun getPassengerByType(type: String) : PassengerDataModel
}