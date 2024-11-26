package ir.ha.meproject.di

import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ir.ha.meproject.data.repository.UserRepository
import ir.ha.meproject.data.repository.UserRepositoryImpl
import ir.ha.meproject.domain.UserUseCase
import ir.ha.meproject.domain.UserUseCaseImpl
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object Modules {

    @Singleton
    @Provides
    fun provideContext(@ApplicationContext appContext: Context): Context = appContext

    @Singleton
    @Provides
    fun provideUserUseCase(userRepository: UserRepository): UserUseCase {
        return UserUseCaseImpl(userRepository)
    }

    @Singleton
    @Provides
    fun provideUserRepository(): UserRepository {
        return UserRepositoryImpl()
    }


}



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
        okHttpClient: OkHttpClient) : Retrofit.Builder{

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


@Module
@InstallIn(SingletonComponent::class)
object UseCasesModule{

    @Provides
    @Singleton
    fun provideSplashApiCallsUseCase(apiCallsRepository: SplashApiCallsRepository) : SplashApiCallsUseCase {
        return SplashApiCallsUseCaseImpl(apiCallsRepository)
    }

}




@Module
@InstallIn(SingletonComponent::class)
object RepositoryModules{

    @Provides
    @Singleton
    fun provideSplashApiCallsRepository(apiServices: ApiServices) : SplashApiCallsRepository {
        return SplashApiCallsRepositoryImpl(apiServices)
    }
}
