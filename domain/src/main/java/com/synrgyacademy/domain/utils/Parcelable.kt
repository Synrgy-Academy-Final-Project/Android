package com.synrgyacademy.domain.utils

import android.os.Build
import android.os.Parcel
import android.os.Parcelable

inline fun <reified T : Parcelable> Parcel.readParcelableCompat(loader: ClassLoader?): T? {
    return if (Build.VERSION.SDK_INT >= 33) {
        readParcelable(loader, T::class.java)
    } else {
        @Suppress("DEPRECATION")
        readParcelable(loader)
    }
}