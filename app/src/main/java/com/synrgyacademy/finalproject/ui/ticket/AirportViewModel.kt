package com.synrgyacademy.finalproject.ui.ticket

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.synrgyacademy.common.Resource
import com.synrgyacademy.domain.model.airport.AirportDataModel
import com.synrgyacademy.domain.model.airport.PassengerTotal
import com.synrgyacademy.domain.usecase.airport.DeleteAllHistoryUseCase
import com.synrgyacademy.domain.usecase.airport.GetAirportUseCase
import com.synrgyacademy.domain.usecase.airport.GetAllHistoryUseCase
import com.synrgyacademy.domain.usecase.airport.GetHistorySearchingByIdUseCase
import com.synrgyacademy.domain.usecase.airport.GetMinimumPriceUseCase
import com.synrgyacademy.domain.usecase.airport.GetScheduleFlightUseCase
import com.synrgyacademy.domain.usecase.airport.InsertHistorySearchingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class AirportViewModel @Inject constructor(
    private val getAirportUseCase: GetAirportUseCase,
    private val getScheduleFlightUseCase: GetScheduleFlightUseCase,
    private val getMinimumPriceUseCase: GetMinimumPriceUseCase,
    private val insertHistorySearchingUseCase: InsertHistorySearchingUseCase,
    private val getAllHistoryUseCase: GetAllHistoryUseCase,
    private val getHistorySearchingByIdUseCase: GetHistorySearchingByIdUseCase,
    private val deleteAllHistoryUseCase: DeleteAllHistoryUseCase
) : ViewModel() {

    private val _airportData = MutableLiveData<AirportState>()
    val airportState: LiveData<AirportState> get() = _airportData

    private val _scheduleData = MutableLiveData<ScheduleState>()
    val scheduleState: LiveData<ScheduleState> get() = _scheduleData

    private val arrivalData = MutableLiveData<AirportDataModel>()
    val arrivalDataState: LiveData<AirportDataModel> get() = arrivalData

    private val departureData = MutableLiveData<AirportDataModel>()
    val departureDataState: LiveData<AirportDataModel> get() = departureData

    private val passengerTotal = MutableLiveData<PassengerTotal>()
    val passengerTotalState: LiveData<PassengerTotal> get() = passengerTotal

    private val _minimumPrice = MutableLiveData<MinimumPriceState>()
    val minimumPrice: LiveData<MinimumPriceState> get() = _minimumPrice

    fun setArrivalData(data: AirportDataModel) {
        arrivalData.value = data
    }

    fun setDepartureData(data: AirportDataModel) {
        departureData.value = data
    }

    fun setPassengerTotal(adult: Int, child: Int, infant: Int) {
        passengerTotal.value = PassengerTotal(adult, child, infant)
    }

    fun getAirport(query: String? = null) {
        getAirportUseCase(query).onEach { result ->
            when (result) {
                is Resource.Loading -> _airportData.value = AirportState.Loading
                is Resource.Error -> _airportData.value = AirportState.Error(result.error)
                is Resource.Success -> _airportData.value = AirportState.Success(result.data)
            }
        }.launchIn(viewModelScope)
    }

    fun getScheduleFlight(
        departureCode: String,
        arrivalCode: String,
        departureDate: String,
        airplaneClass: String
    ) {
        getScheduleFlightUseCase(
            departureCode = departureCode,
            arrivalCode = arrivalCode,
            departureDate = departureDate,
            airplaneClass = airplaneClass
        ).onEach { result ->
            when (result) {
                is Resource.Loading -> _scheduleData.value = ScheduleState.Loading
                is Resource.Error -> _scheduleData.value = ScheduleState.Error(result.error)
                is Resource.Success -> _scheduleData.value = ScheduleState.Success(result.data)
            }
        }.launchIn(viewModelScope)
    }

    fun getMinimumPrice(
        fromAirportCode: String,
        toAirportCode: String,
        departureDate: String
    ) {
        getMinimumPriceUseCase(
            fromAirportCode = fromAirportCode,
            toAirportCode = toAirportCode,
            departureDate = departureDate
        ).onEach { result ->
            when (result) {
                is Resource.Loading -> _minimumPrice.value = MinimumPriceState.Loading
                is Resource.Error -> _minimumPrice.value = MinimumPriceState.Error(result.error)
                is Resource.Success -> _minimumPrice.value = MinimumPriceState.Success(result.data)
            }
        }.launchIn(viewModelScope)
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

}