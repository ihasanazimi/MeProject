package ir.ha.meproject.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.facebook.stetho.okhttp3.StethoInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import ir.ha.meproject.data.remote.ApiServices
import ir.ha.meproject.helper.MockWebServerDispatcher
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockWebServer
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import javax.net.ssl.SSLSession


@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [NetworkModule::class]
)
object NetworkModuleTest {

    @Provides
    @Singleton
    fun provideUrl(): String = "http://127.0.0.1:8080/"

    @Provides
    @Singleton
    fun provideMockWebServer(): MockWebServer = MockWebServer()


    @Singleton
    @Provides
    fun provideMockWebServerDispatcher() = MockWebServerDispatcher()


    @Singleton
    @Provides
    fun provideOkHttpClient(
        context: Context,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .followSslRedirects(false)
            .addInterceptor(
                ChuckerInterceptor.Builder(context)
                    .redactHeaders("Auth-Token", "Bearer")
                    .build()
            )
            .addNetworkInterceptor(StethoInterceptor())
            .hostnameVerifier { hostname: String, session: SSLSession -> true }
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        mockWebServer: MockWebServer ,
        baseUrl: String,
        okHttpClient: OkHttpClient
    ): Retrofit.Builder {

        val mockUrl = mockWebServer.url(baseUrl).toString()
        return Retrofit.Builder()
            .baseUrl(mockUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
    }


    @Provides
    @Singleton
    fun provideApiCallWebService(retrofit: Retrofit.Builder): ApiServices {
        return retrofit.build().create(ApiServices::class.java)
    }
}
