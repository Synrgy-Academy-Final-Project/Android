package com.synrgyacademy.domain.model.airport

import android.os.Parcel
import android.os.Parcelable

data class PopularPlacesDataModel (
    val image: String,
    val name: String,
    val location: String,
    val description: String,
    val likesTotal: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(image)
        parcel.writeString(name)
        parcel.writeString(location)
        parcel.writeString(description)
        parcel.writeInt(likesTotal)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PopularPlacesDataModel> {
        override fun createFromParcel(parcel: Parcel): PopularPlacesDataModel {
            return PopularPlacesDataModel(parcel)
        }

        override fun newArray(size: Int): Array<PopularPlacesDataModel?> {
            return arrayOfNulls(size)
        }
    }
}