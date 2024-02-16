package com.synrgyacademy.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.synrgyacademy.data.local.model.HistorySearchingEntity
import com.synrgyacademy.data.local.model.PassengerEntity
import com.synrgyacademy.data.local.model.TourismEntity
import com.synrgyacademy.data.mapper.Converters

@Database(
    entities = [HistorySearchingEntity::class, PassengerEntity::class, TourismEntity::class],
    version = 3,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AirplaneDatabase : RoomDatabase() {
    abstract fun airplaneDao(): HistorySearchingDao

    abstract fun passengerDao(): PassengerDao

    abstract fun tourismDao(): TourismDao
}