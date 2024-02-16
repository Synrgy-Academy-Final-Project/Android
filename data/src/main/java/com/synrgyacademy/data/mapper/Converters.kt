package com.synrgyacademy.data.mapper

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.synrgyacademy.domain.model.airport.AirportDataModel
import com.synrgyacademy.domain.model.passenger.PassengerTotal

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromAirportDataModel(value: AirportDataModel): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toAirportDataModel(value: String): AirportDataModel {
        return gson.fromJson(value, AirportDataModel::class.java)
    }

    @TypeConverter
    fun fromPassengerTotal(value: PassengerTotal): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toPassengerTotal(value: String): PassengerTotal {
        return gson.fromJson(value, PassengerTotal::class.java)
    }
}