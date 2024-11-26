//package ir.ha.meproject.di
//
//import dagger.Module
//import dagger.Provides
//import dagger.hilt.components.SingletonComponent
//import dagger.hilt.testing.TestInstallIn
//import ir.ha.meproject.data.repository.SplashApiCallsRepository
//import ir.ha.meproject.domain.SplashApiCallsUseCase
//import ir.ha.meproject.domain.SplashApiCallsUseCaseImpl
//import javax.inject.Singleton
//
//
//@Module
//@TestInstallIn(
//    components = [SingletonComponent::class],
//    replaces = [UseCasesModule::class]
//)
//object TestUseCaseModule {
//
//    @Provides
//    @Singleton
//    fun provideSplashApiCallsUseCase(apiCallsRepository: SplashApiCallsRepository) : SplashApiCallsUseCase {
//        return SplashApiCallsUseCaseImpl(apiCallsRepository)
//    }
//
//
//}