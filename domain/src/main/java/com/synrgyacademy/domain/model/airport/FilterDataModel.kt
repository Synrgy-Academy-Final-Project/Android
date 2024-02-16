package com.synrgyacademy.domain.model.airport

data class FilterDataModel(
    val time: String? = null,
    val priceStart: Float,
    val priceEnd: Float,
    val bagasi: Boolean? = null,
    val hiburan: Boolean? = null,
    val makanan: Boolean? = null,
    val stopkontak: Boolean? = null,
    val wifi: Boolean? = null,
    val refundable: Boolean? = null,
    val reschedule: Boolean? = null,
)