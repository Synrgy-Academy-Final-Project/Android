package com.synrgyacademy.domain.model.airport

data class PromotionsDataModel (
    val id: String,

    val title: String,

    val description: String,

    val code: String,

    val discount: Int,

    val terms: String,

    val startDate: String,

    val endDate: String
)