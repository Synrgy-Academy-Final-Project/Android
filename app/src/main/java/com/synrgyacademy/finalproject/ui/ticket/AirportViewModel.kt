package com.synrgyacademy.finalproject.ui.ticket

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.synrgyacademy.common.Resource
import com.synrgyacademy.domain.model.airport.AirportDataModel
import com.synrgyacademy.domain.model.airport.FilterDataModel
import com.synrgyacademy.domain.model.airport.PopularPlacesDataModel
import com.synrgyacademy.domain.model.passenger.PassengerTotal
import com.synrgyacademy.domain.model.query.GetScheduleFlightQuery
import com.synrgyacademy.domain.model.query.MinimumPriceQuery
import com.synrgyacademy.domain.usecase.airport.GetAirportUseCase
import com.synrgyacademy.domain.usecase.airport.GetAllPromotionsUseCase
import com.synrgyacademy.domain.usecase.airport.GetMinimumPriceUseCase
import com.synrgyacademy.domain.usecase.airport.GetScheduleFlightUseCase
import com.synrgyacademy.domain.usecase.filter.GetFilterSearchUseCase
import com.synrgyacademy.domain.usecase.filter.SavedFilterUseCase
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
    private val savedFilterUseCase: SavedFilterUseCase
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

    private val _popularPlace = MutableLiveData<List<PopularPlacesDataModel>>()
    val popularPlace: LiveData<List<PopularPlacesDataModel>> get() = _popularPlace

    private val _allPromotion = MutableLiveData<AllPromotionsState>()
    val allPromotion: LiveData<AllPromotionsState> get() = _allPromotion

    private val _getFilter = MutableLiveData<GetFilterState>()
    val getFilter: LiveData<GetFilterState> get() = _getFilter

    private val _savedFilter = MutableLiveData<FilterState>()

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
        val dummyData = listOf(
            PopularPlacesDataModel(
                name = "Gunung Bromo",
                location = "Malang, Jawa Timur",
                image = "https://ik.imagekit.io/tvlk/blog/2022/09/Wisata-Gunung-Bromo-Traveloka-Xperience-1.jpg?tr=dpr-2,w-675",
                description = "Gunung Bromo adalah salah satu gunung api yang masih aktif di Indonesia. Gunung yang memiliki ketinggian 2.392 meter di atas permukaan laut ini merupakan destinasi andalan Jawa Timur. Gunung Bromo berdiri gagah dikelilingi kaldera atau lautan pasir seluas 10 kilometer persegi. Wisatawan yang berkunjung ke Gunung Bromo akan disambut oleh pemandangan yang indah. Salah satu hal yang terkenal dari Gunung Bromo adalah golden sunrise-nya, pasalnya, Gunung Bromo dinobatkan sebagai tempat yang menawarkan pemandangan matahari terbit terbaik di Pulau Jawa. Sesaat setelah momen matahari terbit berakhir, wisatawan akan kembali disuguhkan pemandangan yang tak kalah indanya, yaitu pemandangan negeri di atas awan.",
                likesTotal = 32
            ),
            PopularPlacesDataModel(
                name = "Candi Prambanan",
                location = "Sleman, Daerah Istimewa Yogyakarta",
                image = "https://borobudurpark.com/wp-content/uploads/2017/05/homepage-portrait-prambanan.jpg",
                description = "Candi Prambanan adalah candi Hindu terbesar di Indonesia. Candi ini terletak di Klaten, Jawa Tengah, sekitar 17 kilometer dari pusat kota Yogyakarta. Candi Prambanan merupakan salah satu candi yang paling terkenal di Indonesia. Candi ini terdiri dari 240 candi kecil yang terletak di sekitar candi utama. Candi Prambanan merupakan candi Hindu terbesar di Indonesia, dan salah satu yang terbesar di Asia Tenggara. Candi ini merupakan salah satu warisan budaya dunia yang diakui oleh UNESCO. Candi Prambanan merupakan candi yang dibangun pada abad ke-9 oleh Wangsa Sanjaya. Candi ini merupakan candi Hindu terbesar di Indonesia, dan salah satu yang terbesar di Asia Tenggara. Candi ini merupakan salah satu warisan budaya dunia yang diakui oleh UNESCO. Candi Prambanan merupakan candi yang dibangun pada abad ke-9 oleh Wangsa Sanjaya.",
                likesTotal = 103
            ),
            PopularPlacesDataModel(
                name = "Pantai Kuta",
                location = "Badung, Bali",
                image = "https://i2.wp.com/blog.tripcetera.com/id/wp-content/uploads/2020/03/leebudihart_76864081_2484833498431751_3194446755026370817_n.jpg",
                description = "Pantai Kuta adalah salah satu pantai yang terkenal di Bali. Pantai ini terletak di Kecamatan Kuta, Kabupaten Badung, Bali. Pantai Kuta merupakan salah satu pantai yang paling terkenal di Bali. Pantai ini terletak di Kecamatan Kuta, Kabupaten Badung, Bali. Pantai ini terkenal dengan pasir putihnya yang lembut dan ombaknya yang besar. Pantai ini merupakan salah satu pantai yang paling terkenal di Bali. Pantai ini terletak di Kecamatan Kuta, Kabupaten Badung, Bali. Pantai ini terkenal dengan pasir putihnya yang lembut dan ombaknya yang besar. Pantai ini merupakan salah satu pantai yang paling terkenal di Bali. Pantai ini terletak di Kecamatan Kuta, Kabupaten Badung, Bali. Pantai ini terkenal dengan pasir putihnya yang lembut dan ombaknya yang besar.",
                likesTotal = 43
            ),
            PopularPlacesDataModel(
                name = "Taman Nasional Gunung Leuser",
                location = "Aceh Tenggara, Aceh",
                image = "https://cdn.idntimes.com/content-images/community/2022/07/zyro-image-3-4c4e0935346233c8fec14261a45ff371-e2f6ef5c3686feda3025baa955b72eb8_600x400.png",
                description = "Taman Nasional Gunung Leuser adalah taman nasional yang terletak di Aceh dan Sumatera Utara. Taman Nasional Gunung Leuser merupakan taman nasional yang terletak di Aceh dan Sumatera Utara. Taman nasional ini merupakan salah satu taman nasional yang paling terkenal di Indonesia. Taman Nasional Gunung Leuser merupakan taman nasional yang terletak di Aceh dan Sumatera Utara. Taman nasional ini merupakan salah satu taman nasional yang paling terkenal di Indonesia. Taman Nasional Gunung Leuser merupakan taman nasional yang terletak di Aceh dan Sumatera Utara. Taman nasional ini merupakan salah satu taman nasional yang paling terkenal di Indonesia. Taman Nasional Gunung Leuser merupakan taman nasional yang terletak di Aceh dan Sumatera Utara. Taman nasional ini merupakan salah satu taman nasional yang paling terkenal di Indonesia.",
                likesTotal = 23
            ),
            PopularPlacesDataModel(
                name = "Pulau Komodo",
                location = "Manggarai Barat, Nusa Tenggara Timur",
                image = "https://unair.ac.id/wp-content/uploads/2020/11/Ilustrasi-oleh-detikTravel.jpeg",
                description = "Pulau Komodo adalah pulau yang terletak di Nusa Tenggara Timur. Pulau ini merupakan salah satu pulau yang paling terkenal di Indonesia. Pulau Komodo merupakan pulau yang terletak di Nusa Tenggara Timur. Pulau ini merupakan salah satu pulau yang paling terkenal di Indonesia. Pulau Komodo merupakan pulau yang terletak di Nusa Tenggara Timur. Pulau ini merupakan salah satu pulau yang paling terkenal di Indonesia. Pulau Komodo merupakan pulau yang terletak di Nusa Tenggara Timur. Pulau ini merupakan salah satu pulau yang paling terkenal di Indonesia.",
                likesTotal = 12
            ),
        )
        _popularPlace.postValue(dummyData)
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