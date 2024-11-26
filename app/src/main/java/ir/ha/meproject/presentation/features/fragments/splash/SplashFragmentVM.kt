package ir.ha.meproject.presentation.features.fragments.splash

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.ha.meproject.common.base.BaseViewModel
import ir.ha.meproject.data.model.ResponseState
import ir.ha.meproject.data.model.SampleObject
import ir.ha.meproject.di.CoroutineDispatchers
import ir.ha.meproject.domain.ApiCallsUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SplashFragmentVM @Inject constructor(
    private val apiCallsUseCase: ApiCallsUseCase,
    private val coroutineDispatchers: CoroutineDispatchers
): BaseViewModel() {

    private val _apiCallResult = MutableSharedFlow<ResponseState<SampleObject>>()
    val apiCallResult = _apiCallResult

    fun apiCall(){
        viewModelScope.launch(coroutineDispatchers.ioDispatchers()) {
            apiCallsUseCase.apiCall1().collect{
                _apiCallResult.emit(it)
            }

        }
    }

}