package com.synrgyacademy.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.synrgyacademy.data.local.model.TourismEntity

@Dao
interface TourismDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTourism(tourismEntity: List<TourismEntity>)

    @Query("DELETE FROM tourism_place")
    suspend fun deleteAll()

    @Query("SELECT * FROM tourism_place")
    suspend fun getTourism() : List<TourismEntity>

    @Query("SELECT * FROM tourism_place WHERE (tourismName LIKE :keyword OR tourismLocation LIKE :keyword OR description LIKE :keyword)")
    suspend fun searchTourism(keyword: String) : List<TourismEntity>
}