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
import okhttp3.mockwebserver.MockWebServer
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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
    fun provideMockWebServer(): MockWebServer = runBlocking {
        val mockWebServer = MockWebServer()
        mockWebServer
    }

    @Provides
    @Singleton
    fun provideRetrofit(mockWebServer: MockWebServer , baseUrl: String): Retrofit.Builder {
        val mockUrl = mockWebServer.url(baseUrl).toString()
        return Retrofit.Builder()
            .baseUrl(mockUrl)
            .addConverterFactory(GsonConverterFactory.create())
    }


    @Provides
    @Singleton
    fun provideApiCallWebService(retrofit: Retrofit.Builder): ApiServices {
        return retrofit.build().create(ApiServices::class.java)
    }
}
