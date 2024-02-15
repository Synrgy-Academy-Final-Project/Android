package com.synrgyacademy.data.remote.response

import com.google.gson.annotations.SerializedName

data class Sort(

    @field:SerializedName("sorted")
    val sorted: Boolean? = null,

    @field:SerializedName("unsorted")
    val unsorted: Boolean? = null,

    @field:SerializedName("empty")
    val empty: Boolean? = null
)
