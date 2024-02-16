package com.synrgyacademy.finalproject.ui.popular

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.synrgyacademy.common.Resource
import com.synrgyacademy.domain.usecase.tourism.GetAllTourismUseCase
import com.synrgyacademy.domain.usecase.tourism.LikedTourismUseCase
import com.synrgyacademy.domain.usecase.tourism.SearchTourismUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class PopularViewModel @Inject constructor(
    private val getAllTourismUseCase: GetAllTourismUseCase,
    private val searchTourismDataModel: SearchTourismUseCase,
    private val likedTourismUseCase: LikedTourismUseCase
) : ViewModel() {

    private var _getTourism = MutableLiveData<TourismState>()
    val getTourism: MutableLiveData<TourismState> get() = _getTourism

    private var _likedTourism = MutableLiveData<TourismLikedState>()
    val likedTourism: MutableLiveData<TourismLikedState> get() = _likedTourism

    fun getAllTourism() {
        getAllTourismUseCase().onEach { result ->
            when (result) {
                is Resource.Loading -> _getTourism.value = TourismState.Loading
                is Resource.Error -> _getTourism.value = TourismState.Error(result.error)
                is Resource.Success -> _getTourism.value = TourismState.Success(result.data)
            }
        }.launchIn(viewModelScope)
    }

    fun searchTourism(data: String) {
        searchTourismDataModel(data).onEach { result ->
            when (result) {
                is Resource.Loading -> _getTourism.value = TourismState.Loading
                is Resource.Error -> _getTourism.value = TourismState.Error(result.error)
                is Resource.Success -> _getTourism.value = TourismState.Success(result.data)
            }
        }.launchIn(viewModelScope)
    }

    fun likedTourism(id: String) {
        likedTourismUseCase(id).onEach { result ->
            when (result) {
                is Resource.Loading -> _likedTourism.value = TourismLikedState.Loading
                is Resource.Error -> _likedTourism.value = TourismLikedState.Error(result.error)
                is Resource.Success -> _likedTourism.value = TourismLikedState.Success(result.data)
            }
        }.launchIn(viewModelScope)
    }
}