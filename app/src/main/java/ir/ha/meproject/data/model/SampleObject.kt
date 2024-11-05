package ir.ha.meproject.data.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName


@Keep
data class SampleObject(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String
)


@Keep
data class BaseResponse<T>(
    @SerializedName("status") var status: Status = Status(),
    @SerializedName("result") var result: BaseResult<T> = BaseResult()
)

@Keep
data class BaseResult<T>(
    @SerializedName("status") var status: Status = Status(),
    @SerializedName("data") var data: T? = null
)

@Keep
data class Status(
    @SerializedName("code") val code: Int = 0,
    @SerializedName("message") val message: String = ""
) {

    fun isSuccess(): Boolean {
        return code in 200..299
    }
}



@Keep
sealed class ResponseState<out T>(
    val data: T? = null,
    val localException: LocalException? = null
) {
    @Keep
    class Success<T>(data: T? = null) : ResponseState<T>(data)
    @Keep
    class Error<T>(localException: LocalException, data: T? = null) : ResponseState<T>(data, localException)
    @Keep
    object Loading : ResponseState<Nothing>()
}


@Keep
data class LocalException(
    val httpCode: Int = -1,
    val errorMessage: String = "",
    val localCode : Int = -1
) : Exception()