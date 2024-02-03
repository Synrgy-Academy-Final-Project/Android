package com.synrgyacademy.data.remote.response

import com.google.gson.annotations.SerializedName

data class Pageable(

    @field:SerializedName("pageNumber")
    val pageNumber: Int? = null,

    @field:SerializedName("pageSize")
    val pageSize: Int? = null,

    @field:SerializedName("sort")
    val sort: Sort? = null,

    @field:SerializedName("offset")
    val offset: Int? = null,

    @field:SerializedName("paged")
    val paged: Boolean? = null,

    @field:SerializedName("unpaged")
    val unpaged: Boolean? = null,

    )