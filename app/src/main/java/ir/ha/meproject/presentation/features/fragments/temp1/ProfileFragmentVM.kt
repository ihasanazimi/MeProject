package ir.ha.meproject.presentation.features.fragments.temp1

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.ha.meproject.common.base.BaseViewModel
import ir.ha.meproject.data.model.User
import ir.ha.meproject.domain.UserUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileFragmentVM @Inject constructor(
    private val userUseCase: UserUseCase
) :  BaseViewModel() {

    val usersFlow = MutableStateFlow<List<User>>(arrayListOf())

    fun getAllUsers() {
        viewModelScope.launch {
            userUseCase.getAllUsers().collect{
                usersFlow.emit(it)
            }
        }
    }


}