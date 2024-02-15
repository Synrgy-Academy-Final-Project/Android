package com.synrgyacademy.finalproject.ui.transaction

import com.synrgyacademy.domain.model.airport.HistoryTransactionDataModel
import com.synrgyacademy.domain.model.airport.PromotionsDataModel
import com.synrgyacademy.domain.model.airport.TransactionDataModel

sealed interface TransactionState {
    data object Loading : TransactionState
    data class Success(val data: TransactionDataModel) : TransactionState
    data class Error(val error: String) : TransactionState
}

sealed interface HistoryTransactionState {
    data object Loading : HistoryTransactionState
    data class Success(val data: HistoryTransactionDataModel) : HistoryTransactionState
    data class Error(val error: String) : HistoryTransactionState
}

sealed interface PromotionState {
    data object Loading : PromotionState
    data class Success(val data: PromotionsDataModel) : PromotionState
    data class Error(val error: String) : PromotionState
}

sealed interface EticketState {
    data object Loading : EticketState
    data class Success(val data: String) : EticketState
    data class Error(val error: String) : EticketState
}