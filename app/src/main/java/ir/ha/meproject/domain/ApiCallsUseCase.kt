package ir.ha.meproject.domain

import android.util.Log
import ir.ha.meproject.data.model.ResponseState
import ir.ha.meproject.data.model.SampleObject
import ir.ha.meproject.data.repository.ApiCallsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface ApiCallsUseCase {

    suspend fun apiCall1() : Flow<ResponseState<SampleObject>>

}

class ApiCallsUseCaseImpl @Inject constructor(
    private val apiCallsRepository: ApiCallsRepository
) : ApiCallsUseCase {

    private val TAG = this::class.java.simpleName


    override suspend fun apiCall1(): Flow<ResponseState<SampleObject>> {
        return apiCallsRepository.apiCall1().also {
            Log.i(TAG, "apiCall1: ")
        }
    }


}




