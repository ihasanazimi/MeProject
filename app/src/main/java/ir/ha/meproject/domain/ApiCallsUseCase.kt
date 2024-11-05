package ir.ha.meproject.domain

import android.util.Log
import ir.ha.meproject.data.model.ResponseState
import ir.ha.meproject.data.model.SampleObject
import ir.ha.meproject.data.repository.SplashApiCallsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface SplashApiCallsUseCase {

    suspend fun apiCall1() : Flow<ResponseState<SampleObject>>

}

class SplashApiCallsUseCaseImpl @Inject constructor(
    private val splashApiCallsRepository: SplashApiCallsRepository
) : SplashApiCallsUseCase {

    private val TAG = this::class.java.simpleName


    override suspend fun apiCall1(): Flow<ResponseState<SampleObject>> {
        return splashApiCallsRepository.apiCall1().also {
            Log.i(TAG, "apiCall1: ")
        }
    }


}




