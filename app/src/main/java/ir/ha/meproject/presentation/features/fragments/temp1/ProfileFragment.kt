package ir.ha.meproject.presentation.features.fragments.temp1

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import ir.ha.meproject.common.base.BaseFragment
import ir.ha.meproject.databinding.FragmentTemp1Binding
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentTemp1Binding>(FragmentTemp1Binding::inflate) {

    private val viewModel: ProfileFragmentVM by viewModels()

    override fun initializing() {
        super.initializing()
        viewModel.getAllUsers()
    }

    override fun observers() {
        super.observers()

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.usersFlow.collect { users ->
                Log.i(TAG, "observers: users list is -> $users ")
            }
        }

    }
}