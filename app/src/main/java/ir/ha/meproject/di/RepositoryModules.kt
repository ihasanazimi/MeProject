package ir.ha.meproject.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ir.ha.meproject.data.remote.ApiServices
import ir.ha.meproject.data.repository.SplashApiCallsRepository
import ir.ha.meproject.data.repository.SplashApiCallsRepositoryImpl
import ir.ha.meproject.data.repository.UserRepository
import ir.ha.meproject.data.repository.UserRepositoryImpl
import ir.ha.meproject.domain.UserUseCase
import ir.ha.meproject.domain.UserUseCaseImpl
import javax.inject.Singleton



@Module
@InstallIn(SingletonComponent::class)
object RepositoryModules{

    @Provides
    @Singleton
    fun provideSplashApiCallsRepository(apiServices: ApiServices) : SplashApiCallsRepository {
        return SplashApiCallsRepositoryImpl(apiServices)
    }

    @Singleton
    @Provides
    fun provideUserRepository(): UserRepository {
        return UserRepositoryImpl()
    }

}
