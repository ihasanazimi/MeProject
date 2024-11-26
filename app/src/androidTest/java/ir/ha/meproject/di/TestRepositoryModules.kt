//package ir.ha.meproject.di
//
//import dagger.Module
//import dagger.Provides
//import dagger.hilt.components.SingletonComponent
//import dagger.hilt.testing.TestInstallIn
//import ir.ha.meproject.data.remote.ApiServices
//import ir.ha.meproject.data.repository.SplashApiCallsRepository
//import ir.ha.meproject.data.repository.SplashApiCallsRepositoryImpl
//import javax.inject.Singleton
//
//
//@Module
//@TestInstallIn(
//    components = [SingletonComponent::class],
//    replaces = [RepositoryModules::class]
//)
//object TestRepositoryModules {
//
//    @Provides
//    @Singleton
//    fun provideSplashApiCallsRepository(apiServices: ApiServices) : SplashApiCallsRepository {
//        return SplashApiCallsRepositoryImpl(apiServices)
//    }
//
//
//}