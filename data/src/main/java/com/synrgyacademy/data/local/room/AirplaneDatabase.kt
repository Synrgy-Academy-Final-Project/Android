package com.synrgyacademy.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.synrgyacademy.data.local.model.AirplaneEntity

@Database(
    entities = [AirplaneEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AirplaneDatabase : RoomDatabase() {
    abstract fun airplaneDao(): AirplaneDao
}