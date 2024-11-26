package ir.ha.meproject.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.ha.meproject.data.repository.SampleRepositoryImpl
import ir.ha.meproject.data.repository.ApiCallsRepository
import ir.ha.meproject.data.repository.UserRepository
import ir.ha.meproject.domain.SampleUseCase
import ir.ha.meproject.domain.SampleUseCaseImpl
import ir.ha.meproject.domain.ApiCallsUseCase
import ir.ha.meproject.domain.ApiCallsUseCaseImpl
import ir.ha.meproject.domain.UserUseCase
import ir.ha.meproject.domain.UserUseCaseImpl
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object UseCasesModule{

    @Provides
    @Singleton
    fun provideSplashApiCallsUseCase(apiCallsRepository: ApiCallsRepository) : ApiCallsUseCase {
        return ApiCallsUseCaseImpl(apiCallsRepository)
    }


    @Singleton
    @Provides
    fun provideUserUseCase(userRepository: UserRepository): UserUseCase {
        return UserUseCaseImpl(userRepository)
    }


    @Singleton
    @Provides
    fun provideSampleUseCase(sampleRepository: SampleRepositoryImpl): SampleUseCase {
        return SampleUseCaseImpl(sampleRepository)
    }

}