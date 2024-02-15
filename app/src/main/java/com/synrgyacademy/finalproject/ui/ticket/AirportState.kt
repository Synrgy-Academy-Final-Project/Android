package com.synrgyacademy.finalproject.ui.ticket

import com.synrgyacademy.domain.model.airport.AirportDataModel
import com.synrgyacademy.domain.model.airport.FilterDataModel
import com.synrgyacademy.domain.model.airport.MinimumDataModel
import com.synrgyacademy.domain.model.airport.PopularPlacesDataModel
import com.synrgyacademy.domain.model.airport.PromotionsDataModel
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

sealed interface PopularPlaceState {
    data object Loading : PopularPlaceState
    data class Success(val data: List<PopularPlacesDataModel>) : PopularPlaceState
    data class Error(val error: String) : PopularPlaceState
}

sealed interface AllPromotionsState {
    data object Loading : AllPromotionsState
    data class Success(val data: List<PromotionsDataModel>) : AllPromotionsState
    data class Error(val error: String) : AllPromotionsState
}

sealed interface GetFilterState {
    data object Loading : GetFilterState
    data class Success(val data: FilterDataModel) : GetFilterState
    data class Error(val error: String) : GetFilterState
}

sealed interface FilterState {
    data object Loading : FilterState
    data object Success : FilterState
    data class Error(val error: String) : FilterState
}