package ir.ha.meproject.presentation.features.fragments.splash

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import ir.ha.meproject.common.base.BaseFragment
import ir.ha.meproject.common.espresso_util.IdlingResourcesKeys
import ir.ha.meproject.common.espresso_util.MyCountingIdlingResource
import ir.ha.meproject.common.espresso_util.getIdlingResource
import ir.ha.meproject.common.extensions.safeNavigate
import ir.ha.meproject.data.model.ResponseState
import ir.ha.meproject.databinding.FragmentSplashBinding
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>(FragmentSplashBinding::inflate) {

    private val viewModel by viewModels<SplashFragmentVM>()

    override fun initializing() {
        super.initializing()
        viewModel.callApiCallResult()
    }


    override fun observers() {
        super.observers()

        viewLifecycleOwner.lifecycleScope.launch {

            viewModel.apiCallResult.collect{ result ->
                when(result){
                    is ResponseState.Success ->{
                        Log.i(TAG, "observers ResponseState.Success: ${result.data?.name} ")

                        // do someThing logics..

                        findNavController().safeNavigate(SplashFragmentDirections.actionSplashFragmentToHomeFragment("Hasan")).also {
                            getIdlingResource<MyCountingIdlingResource>(IdlingResourcesKeys.SPLASH).decrement()
                        }
                    }

                    is ResponseState.Loading -> {
                        Log.i(TAG, "observers ResponseState.Loading : ")
                    }

                    is ResponseState.Error -> {
                        Log.i(TAG, "observers ResponseState.Error: ")
                        Snackbar.make(binding.root, "ERROR", Snackbar.LENGTH_LONG).show()
                        binding.tv.text = "ERROR"
                    }

                }
            }

        }

    }
}