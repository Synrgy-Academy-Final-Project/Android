package com.synrgyacademy.finalproject.ui.ticket

import com.synrgyacademy.domain.model.airport.AirportDataModel
import com.synrgyacademy.domain.model.airport.MinimumDataModel
import com.synrgyacademy.domain.model.airport.ScheduleDataModel

sealed interface AirportState {
    data object Loading : AirportState
    data class Success(val data: List<AirportDataModel>) : AirportState
    data class Error(val error: String) : AirportState
}

sealed interface ScheduleState {
    data object Loading : ScheduleState
    data class Success(val data: List<ScheduleDataModel>) : ScheduleState
    data class Error(val error: String) : ScheduleState
}

sealed interface MinimumPriceState {
    data object Loading : MinimumPriceState
    data class Success(val data: List<MinimumDataModel>) : MinimumPriceState
    data class Error(val error: String) : MinimumPriceState
}