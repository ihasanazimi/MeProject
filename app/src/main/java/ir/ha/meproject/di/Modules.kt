package ir.ha.meproject.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.ha.meproject.data.remote.ApiServices
import ir.ha.meproject.data.repository.SplashApiCallsRepository
import ir.ha.meproject.data.repository.SplashApiCallsRepositoryImpl
import ir.ha.meproject.domain.SplashApiCallsUseCase
import ir.ha.meproject.domain.SplashApiCallsUseCaseImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {


    @Provides
    @Singleton
    fun provideUrl(): String = "http://mocki.io/v1//"

    @Named("regular_retrofit")
    @Provides
    @Singleton
    fun provideRetrofit(baseUrl : String) : Retrofit.Builder{
        return Retrofit.Builder()
            .baseUrl(baseUrl)
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
