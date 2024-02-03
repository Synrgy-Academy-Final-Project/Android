package com.synrgyacademy.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.synrgyacademy.data.local.model.AirplaneEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AirplaneDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistorySearching(airplaneEntity: AirplaneEntity)

    @Query("DELETE FROM airplane")
    suspend fun deleteAllHistorySearching()

    @Query("SELECT * FROM airplane")
    fun getAllHistorySearching(): Flow<List<AirplaneEntity>>

    @Query("SELECT * FROM airplane WHERE id = :id")
    fun getHistorySearchingById(id: Int): AirplaneEntity
}