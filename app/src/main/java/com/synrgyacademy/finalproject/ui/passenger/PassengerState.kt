package com.synrgyacademy.finalproject.ui.passenger

import com.synrgyacademy.domain.model.airport.UserData
import com.synrgyacademy.domain.model.auth.UserDataDataModel
import com.synrgyacademy.domain.model.passenger.PassengerDataModel

sealed interface PassengerState {
    data object Loading : PassengerState

    data class Success(val data: UserData) : PassengerState

    data class Error(val error: String) : PassengerState
}

sealed interface UserDataState {
    data object Loading : UserDataState

    data class Success(val data: UserDataDataModel) : UserDataState

    data class Error(val error: String) : UserDataState
}

sealed interface InsertPassengerState {
    data object Loading : InsertPassengerState

    data object Success: InsertPassengerState

    data class Error(val error: String) : InsertPassengerState
}

sealed interface PassengerLocalState {
    data object Loading : PassengerLocalState

    data class Success(val data: PassengerDataModel) : PassengerLocalState

    data class Error(val error: String) : PassengerLocalState
}

sealed interface AllPassengerLocalState {
    data object Loading : AllPassengerLocalState

    data class Success(val data: List<PassengerDataModel>) : AllPassengerLocalState

    data class Error(val error: String) : AllPassengerLocalState
}

sealed interface DeletedPassengerState {
    data object Loading : DeletedPassengerState

    data object Success : DeletedPassengerState

    data class Error(val error: String) : DeletedPassengerState
}