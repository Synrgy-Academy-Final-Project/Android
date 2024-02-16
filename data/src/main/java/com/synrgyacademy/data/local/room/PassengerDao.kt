package com.synrgyacademy.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.synrgyacademy.data.local.model.PassengerEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PassengerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPassenger(passengerEntity: PassengerEntity)

    @Query("DELETE FROM passenger")
    suspend fun deleteAllPassenger()

    @Update
    suspend fun updatePassenger(passengerEntity: PassengerEntity)

    @Query("SELECT COUNT(*) FROM passenger")
    fun getCountPassenger() : Int

    @Query("SELECT * FROM passenger")
    fun getAllPassenger() : Flow<List<PassengerEntity>>

    @Query("SELECT * FROM passenger WHERE idUser = :id")
    fun getPassengerById(id: Int) : PassengerEntity

    @Query("SELECT * FROM passenger WHERE name = :name")
    fun getPassengerByName(name: String) : PassengerEntity

    @Query("SELECT * FROM passenger WHERE type = :type")
    fun getPassengerByType(type: String) : PassengerEntity
}