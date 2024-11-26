package ir.ha.meproject.viewModels

import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.spyk
import ir.ha.meproject.data.model.ResponseState
import ir.ha.meproject.data.model.SampleEntity
import ir.ha.meproject.data.repository.TestCoroutineDispatchersImpl
import ir.ha.meproject.domain.ApiCallsUseCase
import ir.ha.meproject.helper.BaseTest
import ir.ha.meproject.presentation.fragments.features.splash.SplashFragmentVM
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Test

@ExperimentalCoroutinesApi
class SplashFragmentViewModelTest : BaseTest() {

    private lateinit var viewModel: SplashFragmentVM
    private val apiCallsUseCase = mockk<ApiCallsUseCase>()

    private val testDispatcher = StandardTestDispatcher()
    private val coroutineDispatchersImpl = TestCoroutineDispatchersImpl()

    override fun setup() {
        super.setup()
        Dispatchers.setMain(testDispatcher)
        viewModel = spyk(SplashFragmentVM(apiCallsUseCase, coroutineDispatchersImpl))
    }

    override fun tearDown() {
        super.tearDown()
        Dispatchers.resetMain()
    }

    @Test
    fun testForViewModels() = runTest(coroutineDispatchersImpl.ioDispatchers()) {
        val mockedData = SampleEntity(1, "name", "description")
        coEvery { apiCallsUseCase.apiCall() } returns flow { emit(ResponseState.Success(mockedData)) }.flowOn(Dispatchers.IO)
        viewModel.apiCall()
        val result = viewModel.apiCallResult.first()
        assert(result is ResponseState.Success)
        assert(result.data == mockedData)
    }
}