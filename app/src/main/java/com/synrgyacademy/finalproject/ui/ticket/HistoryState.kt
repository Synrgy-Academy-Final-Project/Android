package com.synrgyacademy.finalproject.ui.ticket

import com.synrgyacademy.domain.model.airport.HistoryDataModel

sealed interface HistoryState {
    data object Loading : HistoryState
    data class Success(val data: List<HistoryDataModel>) : HistoryState
    data class Error(val error: String) : HistoryState
}

sealed interface HistoryByIdState {
    data object Loading : HistoryByIdState
    data class Success(val data: HistoryDataModel) : HistoryByIdState
    data class Error(val error: String) : HistoryByIdState
}

sealed interface InsertHistoryState {
    data object Loading : InsertHistoryState
    data object Success : InsertHistoryState
    data class Error(val error: String) : InsertHistoryState
}

sealed interface DeleteHistoryState {
    data object Loading : DeleteHistoryState
    data object Success : DeleteHistoryState
    data class Error(val error: String) : DeleteHistoryState
}