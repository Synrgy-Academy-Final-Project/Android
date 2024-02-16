package com.synrgyacademy.domain.usecase.tourism

import com.synrgyacademy.common.Resource
import com.synrgyacademy.domain.model.tourism.TourismDataModel
import com.synrgyacademy.domain.repository.TourismRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class SearchTourismUseCase @Inject constructor(
    private val repository: TourismRepository
) {
    operator fun invoke(data: String): Flow<Resource<List<TourismDataModel>>> = flow {
        emit(Resource.Loading)
        try {
            val result = repository.getTourismPlacesByName(data)
            emit(Resource.Success(result))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }.flowOn(Dispatchers.IO)
}