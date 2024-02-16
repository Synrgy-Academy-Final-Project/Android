package com.synrgyacademy.finalproject.ui.ticket

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.synrgyacademy.common.Resource
import com.synrgyacademy.domain.model.airport.AirportDataModel
import com.synrgyacademy.domain.model.airport.FilterDataModel
import com.synrgyacademy.domain.model.passenger.PassengerTotal
import com.synrgyacademy.domain.model.query.GetScheduleFlightQuery
import com.synrgyacademy.domain.model.query.MinimumPriceQuery
import com.synrgyacademy.domain.usecase.airport.GetAirportUseCase
import com.synrgyacademy.domain.usecase.airport.GetAllPromotionsUseCase
import com.synrgyacademy.domain.usecase.airport.GetMinimumPriceUseCase
import com.synrgyacademy.domain.usecase.airport.GetScheduleFlightUseCase
import com.synrgyacademy.domain.usecase.filter.GetFilterSearchUseCase
import com.synrgyacademy.domain.usecase.filter.SavedFilterUseCase
import com.synrgyacademy.domain.usecase.tourism.GetAllTourismUseCase
import com.synrgyacademy.finalproject.ui.popular.TourismState
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
    private val getAllPromotionsUseCase: GetAllPromotionsUseCase,
    private val getFilterSearchUseCase: GetFilterSearchUseCase,
    private val savedFilterUseCase: SavedFilterUseCase,
    private val getAllTourismUseCase: GetAllTourismUseCase
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

    private val _allPromotion = MutableLiveData<AllPromotionsState>()
    val allPromotion: LiveData<AllPromotionsState> get() = _allPromotion

    private val _getFilter = MutableLiveData<GetFilterState>()
    val getFilter: LiveData<GetFilterState> get() = _getFilter

    private val _savedFilter = MutableLiveData<FilterState>()

    private val _tourismData = MutableLiveData<TourismState>()
    val tourismData: LiveData<TourismState> get() = _tourismData

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

    fun getScheduleFlight(getScheduleFlightQuery: GetScheduleFlightQuery) {
        getScheduleFlightUseCase(getScheduleFlightQuery).onEach { result ->
            when (result) {
                is Resource.Loading -> _scheduleData.value = ScheduleState.Loading
                is Resource.Error -> _scheduleData.value = ScheduleState.Error(result.error)
                is Resource.Success -> _scheduleData.value = ScheduleState.Success(result.data)
            }
        }.launchIn(viewModelScope)
    }

    fun getPopularPlaces() {
        getAllTourismUseCase().onEach { result ->
            when (result) {
                is Resource.Loading -> _tourismData.value = TourismState.Loading
                is Resource.Error -> _tourismData.value = TourismState.Error(result.error)
                is Resource.Success -> _tourismData.value = TourismState.Success(result.data)
            }
        }.launchIn(viewModelScope)
    }

    fun getAllPromotions() {
        getAllPromotionsUseCase().onEach { result ->
            when (result) {
                is Resource.Loading -> _allPromotion.value = AllPromotionsState.Loading
                is Resource.Error -> _allPromotion.value = AllPromotionsState.Error(result.error)
                is Resource.Success -> _allPromotion.value = AllPromotionsState.Success(result.data)
            }
        }.launchIn(viewModelScope)
    }

    fun getMinimumPrice(minimumPriceQuery: MinimumPriceQuery) {
        getMinimumPriceUseCase(minimumPriceQuery).onEach { result ->
            when (result) {
                is Resource.Loading -> _minimumPrice.value = MinimumPriceState.Loading
                is Resource.Error -> _minimumPrice.value = MinimumPriceState.Error(result.error)
                is Resource.Success -> _minimumPrice.value = MinimumPriceState.Success(result.data)
            }
        }.launchIn(viewModelScope)
    }

    fun getFilterSearch() {
        getFilterSearchUseCase().onEach { result ->
            when (result) {
                is Resource.Loading -> _getFilter.value = GetFilterState.Loading
                is Resource.Error -> _getFilter.value = GetFilterState.Error(result.error)
                is Resource.Success -> _getFilter.value = GetFilterState.Success(result.data)
            }
        }.launchIn(viewModelScope)
    }

    fun savedFilter(data: FilterDataModel) {
        savedFilterUseCase(data).onEach { result ->
            when (result) {
                is Resource.Loading -> _savedFilter.value = FilterState.Loading
                is Resource.Error -> _savedFilter.value = FilterState.Error(result.error)
                is Resource.Success -> _savedFilter.value = FilterState.Success
            }
        }.launchIn(viewModelScope)
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

}