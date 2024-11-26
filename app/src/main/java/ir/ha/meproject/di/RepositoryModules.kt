package ir.ha.meproject.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.ha.meproject.data.remote.ApiServices
import ir.ha.meproject.data.repository.NumberRepository
import ir.ha.meproject.data.repository.NumberRepositoryImpl
import ir.ha.meproject.data.repository.ApiCallsRepository
import ir.ha.meproject.data.repository.ApiCallsRepositoryImpl
import ir.ha.meproject.data.repository.CoroutineDispatchers
import ir.ha.meproject.data.repository.CoroutineDispatchersImpl
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
    fun provideNumberRepository() : NumberRepository {
        return NumberRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideUserRepository() : UserRepository {
        return UserRepositoryImpl()
    }

    @Singleton
    @Provides
    fun provideDispatcher() : CoroutineDispatchers {
        return CoroutineDispatchersImpl()
    }



}
