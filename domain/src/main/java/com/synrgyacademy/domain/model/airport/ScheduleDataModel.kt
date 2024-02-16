package com.synrgyacademy.domain.model.airport

import android.os.Parcel
import android.os.Parcelable
import com.synrgyacademy.domain.utils.readParcelableCompat

data class ScheduleDataModel (
    val companyName: String,
    val urlLogo: String,
    val airplaneId: String,
    val airplaneName: String,
    val airplaneCode: String,
    val airplaneClassId: String,
    val airplaneClass: String,
    val capacity: Int,
    val airplaneServices: ScheduleServicesItem,
    val airplaneFlightTimeId: String,
    val flightTime: String,
    val departureCode: String,
    val departureCityCode: String,
    val departureNameAirport: String,
    val arrivalCode: String,
    val arrivalCityCode: String,
    val arrivalNameAirport: String,
    val departureTime: String,
    val arrivalTime: String,
    val totalPrice: Int,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readParcelableCompat<ScheduleServicesItem>(ScheduleServicesItem::class.java.classLoader)!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(companyName)
        parcel.writeString(urlLogo)
        parcel.writeString(airplaneId)
        parcel.writeString(airplaneName)
        parcel.writeString(airplaneCode)
        parcel.writeString(airplaneClassId)
        parcel.writeString(airplaneClass)
        parcel.writeInt(capacity)
        parcel.writeParcelable(airplaneServices, flags)
        parcel.writeString(airplaneFlightTimeId)
        parcel.writeString(flightTime)
        parcel.writeString(departureCode)
        parcel.writeString(departureCityCode)
        parcel.writeString(departureNameAirport)
        parcel.writeString(arrivalCode)
        parcel.writeString(arrivalCityCode)
        parcel.writeString(arrivalNameAirport)
        parcel.writeString(departureTime)
        parcel.writeString(arrivalTime)
        parcel.writeInt(totalPrice)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ScheduleDataModel> {
        override fun createFromParcel(parcel: Parcel): ScheduleDataModel {
            return ScheduleDataModel(parcel)
        }

        override fun newArray(size: Int): Array<ScheduleDataModel?> {
            return arrayOfNulls(size)
        }
    }
}

data class ScheduleServicesItem(
    val baggage: Int,
    val cabinBaggage: Int,
    val meals: Boolean,
    val travelInsurance: Boolean,
    val inflightEntertainment: Boolean,
    val electricSocket: Boolean,
    val wifi: Boolean,
    val reschedule: Boolean,
    val refund: Int,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(baggage)
        parcel.writeInt(cabinBaggage)
        parcel.writeByte(if (meals) 1 else 0)
        parcel.writeByte(if (travelInsurance) 1 else 0)
        parcel.writeByte(if (inflightEntertainment) 1 else 0)
        parcel.writeByte(if (electricSocket) 1 else 0)
        parcel.writeByte(if (wifi) 1 else 0)
        parcel.writeByte(if (reschedule) 1 else 0)
        parcel.writeInt(refund)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ScheduleServicesItem> {
        override fun createFromParcel(parcel: Parcel): ScheduleServicesItem {
            return ScheduleServicesItem(parcel)
        }

        override fun newArray(size: Int): Array<ScheduleServicesItem?> {
            return arrayOfNulls(size)
        }
    }
}