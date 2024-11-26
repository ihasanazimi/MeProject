package ir.ha.meproject.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.facebook.stetho.okhttp3.StethoInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ir.ha.meproject.data.remote.ApiServices
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton
import javax.net.ssl.SSLSession


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {



    @Provides
    @Singleton
    fun provideUrl(): String = "https://mocki.io/v1/"


    @Provides
    @Singleton
    fun provideContext(@ApplicationContext appContext: Context): Context = appContext


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

    @Named("regular_retrofit")
    @Provides
    @Singleton
    fun provideRetrofit(
        baseUrl : String ,
        okHttpClient: OkHttpClient
    ) : Retrofit.Builder{

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
    }

    @Singleton
    @Provides
    fun provideApiCallWebService(@Named("regular_retrofit") retrofit: Retrofit.Builder) : ApiServices {
        return retrofit.build().create(ApiServices::class.java)
    }


}