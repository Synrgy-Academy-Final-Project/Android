package com.synrgyacademy.domain.model.airport

data class TransactionDataModel(
    val token: String,
    val redirectUrl: String,
    val orderId: String
)
