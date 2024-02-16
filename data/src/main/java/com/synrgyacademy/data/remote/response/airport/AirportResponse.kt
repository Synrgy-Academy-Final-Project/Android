package com.synrgyacademy.data.remote.response.airport

import com.google.gson.annotations.SerializedName
import com.synrgyacademy.data.remote.response.Pageable
import com.synrgyacademy.data.remote.response.Sort

data class AirportResponse(

    @field:SerializedName("data")
    val data: Data? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("status")
    val status: Int? = null
)

data class Data(

    @field:SerializedName("number")
    val number: Int? = null,

    @field:SerializedName("last")
    val last: Boolean? = null,

    @field:SerializedName("size")
    val size: Int? = null,

    @field:SerializedName("numberOfElements")
    val numberOfElements: Int? = null,

    @field:SerializedName("totalPages")
    val totalPages: Int? = null,

    @field:SerializedName("pageable")
    val pageable: Pageable? = null,

    @field:SerializedName("sort")
    val sort: Sort? = null,

    @field:SerializedName("content")
    val airportList: List<AirportListItem>? = null,

    @field:SerializedName("first")
    val first: Boolean? = null,

    @field:SerializedName("totalElements")
    val totalElements: Int? = null,

    @field:SerializedName("empty")
    val empty: Boolean? = null
)

data class AirportListItem(

    @field:SerializedName("airportCityCode")
    val airportCityCode: String? = null,

    @field:SerializedName("airportCityCountry")
    val airportCityCountry: String? = null,

    @field:SerializedName("airportCode")
    val airportCode: String? = null,

    @field:SerializedName("airportCodeName")
    val airportCodeName: String? = null
)
