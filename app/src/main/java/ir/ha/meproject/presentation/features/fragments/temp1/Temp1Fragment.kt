package ir.ha.meproject.presentation.features.fragments.temp1

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import ir.ha.meproject.databinding.FragmentTemp1Binding
import ir.ha.meproject.common.base.BaseFragment
import ir.ha.meproject.common.extensions.safeNavigate
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class Temp1Fragment : BaseFragment<FragmentTemp1Binding>(FragmentTemp1Binding::inflate) {

    private val viewModel by viewModels<Temp1FragmentVM>()


    override fun initializing() {
        super.initializing()
    }

    override fun uiConfig() {
        super.uiConfig()

        viewLifecycleOwner.lifecycleScope.launch {
            delay(2000)
            findNavController().safeNavigate(Temp1FragmentDirections.actionTemp1FragmentToTemp2Fragment("Hasan"))
        }

    }


    override fun listeners() {
        super.listeners()
    }

}