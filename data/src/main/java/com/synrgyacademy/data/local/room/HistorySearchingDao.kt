package com.synrgyacademy.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.synrgyacademy.data.local.model.HistorySearchingEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HistorySearchingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistorySearching(historySearchingEntity: HistorySearchingEntity)

    @Query("DELETE FROM history_searching")
    suspend fun deleteAllHistorySearching()

    @Query("SELECT * FROM history_searching ORDER BY datetime(searchingDate) DESC")
    fun getAllHistorySearching(): Flow<List<HistorySearchingEntity>>

    @Query("SELECT * FROM history_searching WHERE id = :id")
    fun getHistorySearchingById(id: Int): HistorySearchingEntity
}