package ir.ha.meproject.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.ha.meproject.data.repository.SplashApiCallsRepository
import ir.ha.meproject.data.repository.UserRepository
import ir.ha.meproject.domain.SplashApiCallsUseCase
import ir.ha.meproject.domain.SplashApiCallsUseCaseImpl
import ir.ha.meproject.domain.UserUseCase
import ir.ha.meproject.domain.UserUseCaseImpl
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object UseCasesModule{

    @Provides
    @Singleton
    fun provideSplashApiCallsUseCase(apiCallsRepository: SplashApiCallsRepository) : SplashApiCallsUseCase {
        return SplashApiCallsUseCaseImpl(apiCallsRepository)
    }


    @Singleton
    @Provides
    fun provideUserUseCase(userRepository: UserRepository): UserUseCase {
        return UserUseCaseImpl(userRepository)
    }

}