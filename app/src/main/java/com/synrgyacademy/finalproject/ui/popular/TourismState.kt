package com.synrgyacademy.finalproject.ui.popular

import com.synrgyacademy.domain.model.tourism.TourismDataModel

sealed interface TourismState {
    data object Loading : TourismState
    data class Success(val data: List<TourismDataModel>) : TourismState
    data class Error(val error: String) : TourismState
}

sealed interface TourismLikedState {
    data object Loading : TourismLikedState
    data class Success(val data: TourismDataModel) : TourismLikedState
    data class Error(val error: String) : TourismLikedState
}