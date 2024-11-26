package ir.ha.meproject.presentation.fragments.features.splash

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.ha.meproject.common.base.BaseViewModel
import ir.ha.meproject.data.model.ResponseState
import ir.ha.meproject.data.model.SampleEntity
import ir.ha.meproject.data.repository.CoroutineDispatchers
import ir.ha.meproject.domain.ApiCallsUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SplashFragmentVM @Inject constructor(
    private val apiCallsUseCase: ApiCallsUseCase,
    private val coroutineDispatchers: CoroutineDispatchers
): BaseViewModel() {

    private val _apiCallResult = MutableSharedFlow<ResponseState<SampleEntity>>()
    val apiCallResult = _apiCallResult

    fun apiCall(){
        viewModelScope.launch(coroutineDispatchers.ioDispatchers()) {
            apiCallsUseCase.apiCall().collect{
                _apiCallResult.emit(it)
            }

        }
    }

}