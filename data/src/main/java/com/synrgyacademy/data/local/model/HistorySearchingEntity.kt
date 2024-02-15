package com.synrgyacademy.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.synrgyacademy.domain.model.airport.AirportDataModel
import com.synrgyacademy.domain.model.airport.HistoryDataModel
import com.synrgyacademy.domain.model.passenger.PassengerTotal

@Entity(tableName = "history_searching")
data class HistorySearchingEntity (

    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo("departureData")
    val departureData: AirportDataModel,

    @ColumnInfo("arrivalData")
    val arrivalData: AirportDataModel,

    @ColumnInfo(name = "departureDate")
    val departureDate: String,

    @ColumnInfo(name = "searchingDate")
    val searchingDate: String,

    @ColumnInfo(name = "passenger")
    val passenger: PassengerTotal,

    @ColumnInfo(name = "airplaneClass")
    val airplaneClass: String
) {
    fun toHistoryDataModel(): HistoryDataModel{
        return HistoryDataModel(
            id = id,
            departureData = departureData,
            arrivalData = arrivalData,
            departureDate = departureDate,
            searchingDate = searchingDate,
            passenger = passenger,
            airplaneClass = airplaneClass
        )
    }

    companion object {
        fun from(historyDataModel: HistoryDataModel): HistorySearchingEntity {
            return HistorySearchingEntity(
                id = historyDataModel.id,
                departureData = historyDataModel.departureData,
                arrivalData = historyDataModel.arrivalData,
                departureDate = historyDataModel.departureDate,
                searchingDate = historyDataModel.searchingDate,
                passenger = historyDataModel.passenger,
                airplaneClass = historyDataModel.airplaneClass
            )
        }
    }
}