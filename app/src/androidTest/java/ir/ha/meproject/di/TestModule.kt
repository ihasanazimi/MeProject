package ir.ha.meproject.di

import dagger.Module
import dagger.Provides
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
    fun provideMockWebServer(): MockWebServer = runBlocking {
        val mockWebServer = MockWebServer()
        withContext(Dispatchers.IO) { mockWebServer.start(8080) } // Start on IO thread
        mockWebServer
    }

    @Provides
    @Singleton
    fun provideRetrofit(mockWebServer: MockWebServer): Retrofit.Builder {
        val mockUrl = mockWebServer.url("http://127.0.0.1/").toString() // Retrieve base URL in a safe way
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