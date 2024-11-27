package ir.ha.meproject.data.repository

import android.util.Log
import ir.ha.meproject.data.model.LocalException
import ir.ha.meproject.data.model.ResponseState
import ir.ha.meproject.data.model.SampleEntity
import ir.ha.meproject.data.remote.ApiServices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

interface ApiCallsRepository {


    suspend fun apiCall(): Flow<ResponseState<SampleEntity>>

}


class ApiCallsRepositoryImpl @Inject constructor(
    private val apiServices: ApiServices
) : ApiCallsRepository {

    private val TAG = this::class.java.simpleName

    override suspend fun apiCall() = flow {
        try {
            Log.i(TAG, "apiCall")
            emit(ResponseState.Loading)
            val response = apiServices.apiCall1()
            when(response.isSuccessful){

                true -> {
                    response.body()?.result?.data?.let {
                        emit(ResponseState.Success(it))
                    }
                }

                else -> {
                    emit(ResponseState.Error(LocalException()))
                }
            }
        } catch (e : Exception){
            Log.i(TAG, "apiCall: ${e.message} ")
            emit(ResponseState.Error(LocalException()))
        } as Unit
    }.flowOn(Dispatchers.IO)
}

