package com.synrgyacademy.domain.model.airport

import android.os.Parcelable

data class PassengerTotal(
    val adult: Int,
    val child: Int,
    val infant: Int
) : Parcelable {
    constructor(parcel: android.os.Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: android.os.Parcel, flags: Int) {
        parcel.writeInt(adult)
        parcel.writeInt(child)
        parcel.writeInt(infant)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PassengerTotal> {
        override fun createFromParcel(parcel: android.os.Parcel): PassengerTotal {
            return PassengerTotal(parcel)
        }

        override fun newArray(size: Int): Array<PassengerTotal?> {
            return arrayOfNulls(size)
        }
    }
}