package ir.ha.meproject.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import ir.ha.meproject.data.remote.ApiServices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockWebServer
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [NetworkModule::class]
)
object TestNetworkModule {


    @Provides
    @Singleton
    fun provideUrl(): String = "http://localhost:8080/"

    @Provides
    @Singleton
    fun provideMockWebServer(): MockWebServer = MockWebServer()

    @Provides
    @Singleton
    fun provideRetrofit(mockWebServer: MockWebServer , baseUrl: String): Retrofit.Builder {

        val client = OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)   // Connection timeout
            .readTimeout(10, TimeUnit.SECONDS)      // Read timeout
            .writeTimeout(10, TimeUnit.SECONDS)     // Write timeout
            .build()


        val mockUrl = mockWebServer.url(baseUrl).toString()
        return Retrofit.Builder()
            .baseUrl(mockUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
    }


    @Provides
    @Singleton
    fun provideApiCallWebService(retrofit: Retrofit.Builder): ApiServices {
        return retrofit.build().create(ApiServices::class.java)
    }
}
