package com.synrgyacademy.domain.model.tourism

import android.os.Parcel
import android.os.Parcelable

data class TourismDataModel(
    val id: String,

    val tourismName: String,

    val description: String,

    val tourismLocation: String,

    val imageLink: String,

    val likeTotal: Int,

    val createAt: String,

    val updatedAt: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.readString().toString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(tourismName)
        parcel.writeString(description)
        parcel.writeString(tourismLocation)
        parcel.writeString(imageLink)
        parcel.writeInt(likeTotal)
        parcel.writeString(createAt)
        parcel.writeString(updatedAt)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TourismDataModel> {
        override fun createFromParcel(parcel: Parcel): TourismDataModel {
            return TourismDataModel(parcel)
        }

        override fun newArray(size: Int): Array<TourismDataModel?> {
            return arrayOfNulls(size)
        }
    }
}