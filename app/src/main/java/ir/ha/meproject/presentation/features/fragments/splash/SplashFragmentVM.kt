package ir.ha.meproject.presentation.features.fragments.splash

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.ha.meproject.common.base.BaseViewModel
import ir.ha.meproject.data.model.ResponseState
import ir.ha.meproject.data.model.SampleObject
import ir.ha.meproject.domain.SplashApiCallsUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SplashFragmentVM @Inject constructor(
    private val splashApiCallsUseCase: SplashApiCallsUseCase
): BaseViewModel() {

    private val _apiCallResult = MutableSharedFlow<ResponseState<SampleObject>>()
    val apiCallResult = _apiCallResult.asSharedFlow()

    fun callApiCallResult(){
        viewModelScope.launch {
            splashApiCallsUseCase.apiCall1().collect{
                _apiCallResult.emit(it)
            }

        }
    }

}