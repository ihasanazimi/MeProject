package ir.ha.meproject.presentation.features.fragments.splash

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import ir.ha.meproject.common.base.BaseFragment
import ir.ha.meproject.common.espresso_util.MyCountingIdlingResource
import ir.ha.meproject.common.espresso_util.MyIdlingResource
import ir.ha.meproject.common.extensions.safeNavigate
import ir.ha.meproject.databinding.FragmentSplashBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashFragment : BaseFragment<FragmentSplashBinding>(FragmentSplashBinding::inflate) {

    private val viewModel by viewModels<SplashFragmentVM>()

    private val myIdlingResource by lazy { MyIdlingResource(TAG) }
    fun getIdlingResource() = myIdlingResource

    private val myCountingIdlingResource = MyCountingIdlingResource(TAG)
    fun getMyCountingIdlingResource() = myCountingIdlingResource

    override fun uiConfig() {
        super.uiConfig()

        // todo : uncomment this code for myIdlingResource concept
        /*viewLifecycleOwner.lifecycleScope.launch {

            myIdlingResource.setIdleState(false)
            delay(2000) // simulate to api call
            myIdlingResource.setIdleState(true)


            delay(4000) // simulate to preparation of data
            myIdlingResource.setIdleState(false)
            delay(2000) // simulate to loading

            findNavController().safeNavigate(Temp1FragmentDirections.actionTemp1FragmentToTemp2Fragment("Hasan"))
            myIdlingResource.setIdleState(true)
        }*/



        // TODO : uncomment this code for myCountingIdlingResource concept
        viewLifecycleOwner.lifecycleScope.launch {


            myCountingIdlingResource.increment()
            delay(2000) // simulate to api call
            myCountingIdlingResource.decrement()

            myCountingIdlingResource.increment()
            delay(2000) // simulate to loading
            findNavController().safeNavigate(SplashFragmentDirections.actionSplashFragmentToHomeFragment("Hasan")).also {
                myCountingIdlingResource.decrement()
            }
        }

    }


}