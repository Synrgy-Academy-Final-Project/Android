package com.synrgyacademy.domain.model.airport

import android.os.Parcel
import android.os.Parcelable

data class HistoryTransactionDataModel(
    val orderId: String? = null,

    val urlCompany: String? = null,

    val departureCode: String? = null,

    val departureTime: String? = null,

    val departureDate: String? = null,

    val arrivalCode: String? = null,

    val arrivalTime: String? = null,

    val arrivalDate: String? = null,

    val durationAirplane: String? = null,

    val departureCityCode: String? = null,

    val departureAirportName: String? = null,

    val departureCountry: String? = null,

    val arrivalCityCode: String? = null,

    val arrivalAirportName: String? = null,

    val arrivalCountry: String? = null,

    val oderCode: String? = null,

    val paymentMethod: String? = null,

    val transactionStatus: String? = null,

    val totalPrice: Int? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int
    )

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(orderId)
        dest.writeString(urlCompany)
        dest.writeString(departureCode)
        dest.writeString(departureTime)
        dest.writeString(departureDate)
        dest.writeString(arrivalCode)
        dest.writeString(arrivalTime)
        dest.writeString(arrivalDate)
        dest.writeString(durationAirplane)
        dest.writeString(departureCityCode)
        dest.writeString(departureAirportName)
        dest.writeString(departureCountry)
        dest.writeString(arrivalCityCode)
        dest.writeString(arrivalAirportName)
        dest.writeString(arrivalCountry)
        dest.writeString(oderCode)
        dest.writeString(paymentMethod)
        dest.writeString(transactionStatus)
        dest.writeValue(totalPrice)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<HistoryTransactionDataModel> {
        override fun createFromParcel(parcel: Parcel): HistoryTransactionDataModel {
            return HistoryTransactionDataModel(parcel)
        }

        override fun newArray(size: Int): Array<HistoryTransactionDataModel?> {
            return arrayOfNulls(size)
        }
    }
}