package ir.ha.meproject.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.ha.meproject.data.remote.ApiServices
import ir.ha.meproject.data.repository.SampleRepository
import ir.ha.meproject.data.repository.SampleRepositoryImpl
import ir.ha.meproject.data.repository.ApiCallsRepository
import ir.ha.meproject.data.repository.ApiCallsRepositoryImpl
import ir.ha.meproject.data.repository.UserRepository
import ir.ha.meproject.data.repository.UserRepositoryImpl
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModules{

    @Provides
    @Singleton
    fun provideSplashApiCallsRepository(apiServices: ApiServices) : ApiCallsRepository {
        return ApiCallsRepositoryImpl(apiServices)
    }



    @Provides
    @Singleton
    fun provideSampleRepository() : SampleRepository {
        return SampleRepositoryImpl()
    }


    @Provides
    @Singleton
    fun provideUserRepository() : UserRepository {
        return UserRepositoryImpl()
    }



}
