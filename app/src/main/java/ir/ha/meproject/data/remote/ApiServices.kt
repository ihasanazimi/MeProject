package ir.ha.meproject.data.remote

import ir.ha.meproject.data.model.BaseResponse
import ir.ha.meproject.data.model.SampleObject
import retrofit2.Response
import retrofit2.http.GET


interface ApiServices{

    @GET("5be5839a-d088-483f-9270-33df02550b0c")
    suspend fun apiCall1() : Response<BaseResponse<SampleObject>>
}