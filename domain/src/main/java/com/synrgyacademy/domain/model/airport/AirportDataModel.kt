package com.synrgyacademy.domain.model.airport

import android.os.Parcel
import android.os.Parcelable

data class AirportDataModel(

    val airportCityCode: String,

    val airportCityCountry: String,

    val airportCode: String,

    val airportCodeName: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(airportCityCode)
        parcel.writeString(airportCityCountry)
        parcel.writeString(airportCode)
        parcel.writeString(airportCodeName)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AirportDataModel> {
        override fun createFromParcel(parcel: Parcel): AirportDataModel {
            return AirportDataModel(parcel)
        }

        override fun newArray(size: Int): Array<AirportDataModel?> {
            return arrayOfNulls(size)
        }
    }
}