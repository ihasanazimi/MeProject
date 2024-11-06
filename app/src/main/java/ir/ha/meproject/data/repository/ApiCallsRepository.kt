package ir.ha.meproject.data.repository

import android.util.Log
import ir.ha.meproject.common.espresso_util.IdlingResourcesKeys
import ir.ha.meproject.common.espresso_util.MyCountingIdlingResource
import ir.ha.meproject.common.espresso_util.createAndReturnIdlingResource
import ir.ha.meproject.data.model.LocalException
import ir.ha.meproject.data.model.ResponseState
import ir.ha.meproject.data.model.SampleObject
import ir.ha.meproject.data.remote.ApiServices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface SplashApiCallsRepository {


    suspend fun apiCall1(): Flow<ResponseState<SampleObject>>

}


class SplashApiCallsRepositoryImpl @Inject constructor(val apiServices: ApiServices) : SplashApiCallsRepository {

    private val TAG = this::class.java.simpleName

    private lateinit var myIdlingResource : MyCountingIdlingResource

    override suspend fun apiCall1() = flow {
        try {
            myIdlingResource = createAndReturnIdlingResource<MyCountingIdlingResource>(
                key = IdlingResourcesKeys.SPLASH,
                resource = MyCountingIdlingResource(IdlingResourcesKeys.SPLASH.name)
            ) as MyCountingIdlingResource

            Log.i(TAG, "apiCall1: ")
            emit(ResponseState.Loading)
            myIdlingResource.increment()
            val response = apiServices.apiCall1()
            if (response.isSuccessful){
                response.body()?.result?.data?.let {
                    emit(ResponseState.Success(it))
                }
            }
        } catch (e : Exception){
            Log.i(TAG, "apiCall1: ${e.message} ")
            emit(ResponseState.Error(LocalException()))
        }
    }
}

