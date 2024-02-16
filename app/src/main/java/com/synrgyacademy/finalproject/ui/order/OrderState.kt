package com.synrgyacademy.finalproject.ui.order

import com.synrgyacademy.domain.model.airport.HistoryTransactionDataModel

sealed interface OrderState {
    data object Loading : OrderState

    data class Success(val data: List<HistoryTransactionDataModel>) : OrderState

    data class Error(val error: String) : OrderState
}