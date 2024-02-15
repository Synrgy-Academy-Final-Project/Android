package com.synrgyacademy.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "airplane")
data class AirplaneEntity (

    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo(name = "from_city")
    val fromCity: String,

    @ColumnInfo(name = "to_city")
    val toCity: String,

    @ColumnInfo(name = "date")
    val date: String,

    @ColumnInfo(name = "passenger_number")
    val passengerNumber: Int,

    @ColumnInfo(name = "adult_number")
    val adultNumber: Int,

    @ColumnInfo(name = "child_number")
    val childNumber: Int,

    @ColumnInfo(name = "infant_number")
    val infantNumber: Int,

    @ColumnInfo(name = "airplane_class")
    val airplaneClass: String,
)