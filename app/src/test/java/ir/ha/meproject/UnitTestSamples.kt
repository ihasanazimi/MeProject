package ir.ha.meproject

import io.mockk.mockk
import ir.ha.meproject.domain.UserUseCaseImpl
import kotlinx.coroutines.test.StandardTestDispatcher
import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import kotlin.test.Test


@RunWith(MockitoJUnitRunner::class)
class UnitTestSamples {

    val testCoroutine = StandardTestDispatcher()
    val userUseCase : UserUseCaseImpl = mockk()


    @Before
    fun setup(){

    }


    @Test
    fun sampleTest(){

    }



}