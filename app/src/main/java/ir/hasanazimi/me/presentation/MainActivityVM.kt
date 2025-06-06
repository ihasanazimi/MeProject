package ir.hasanazimi.me.presentation

import dagger.hilt.android.lifecycle.HiltViewModel
import ir.hasanazimi.me.common.base.BaseViewModel
import ir.hasanazimi.me.domain.XUseCase
import javax.inject.Inject

@HiltViewModel
class MainActivityVM @Inject constructor(
    private val xUseCase: XUseCase
) : BaseViewModel() {

}