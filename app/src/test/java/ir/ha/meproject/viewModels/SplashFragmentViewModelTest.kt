package ir.ha.meproject.viewModels

import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.spyk
import ir.ha.meproject.data.model.ResponseState
import ir.ha.meproject.data.model.SampleObject
import ir.ha.meproject.domain.SplashApiCallsUseCase
import ir.ha.meproject.helper.BaseTest
import ir.ha.meproject.presentation.features.fragments.splash.SplashFragmentVM
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Test


class SplashFragmentViewModelTest : BaseTest(){

    private lateinit var viewModel: SplashFragmentVM
    private val splashApiCallsUseCase = mockk<SplashApiCallsUseCase>()


    override fun setup() {
        super.setup()
        viewModel = spyk(SplashFragmentVM(splashApiCallsUseCase))
    }


    override fun tearDown() {
        super.tearDown()
    }



    @Test
    fun `testForViewModels`() = runTest {
        val mockedData = SampleObject(1,"name","description")
        coEvery { splashApiCallsUseCase.apiCall1() } returns flow { ResponseState.Success(mockedData) }
        viewModel.callApiCallResult()
        viewModel.apiCallResult.collect{
            assert(it is ResponseState.Success)
            assert(it.data == mockedData)
        }
    }

}