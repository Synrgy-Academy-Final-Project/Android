package com.synrgyacademy.domain.model.passenger

import android.os.Parcel
import android.os.Parcelable

data class PassengerDataModel(
    val id: Int,
    val name: String,
    val bornDate: String,
    val gender: String,
    val phoneNumber: String? = null,
    val type: String,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(bornDate)
        parcel.writeString(gender)
        parcel.writeString(phoneNumber)
        parcel.writeString(type)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PassengerDataModel> {
        override fun createFromParcel(parcel: Parcel): PassengerDataModel {
            return PassengerDataModel(parcel)
        }

        override fun newArray(size: Int): Array<PassengerDataModel?> {
            return arrayOfNulls(size)
        }
    }
}