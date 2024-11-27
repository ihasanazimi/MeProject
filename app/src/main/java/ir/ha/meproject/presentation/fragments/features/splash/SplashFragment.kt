package ir.ha.meproject.presentation.fragments.features.splash

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import ir.ha.meproject.common.base.BaseFragment
import ir.ha.meproject.common.extensions.safeNavigate
import ir.ha.meproject.data.model.ResponseState
import ir.ha.meproject.databinding.FragmentSplashBinding
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>(FragmentSplashBinding::inflate) {

    private val viewModel by viewModels<SplashFragmentVM>()

    override fun initializing() {
        super.initializing()
        viewModel.apiCall()
    }


    override fun observers() {
        super.observers()

        viewLifecycleOwner.lifecycleScope.launch {

            viewModel.apiCallResult.collect{ result ->
                when(result){
                    is ResponseState.Success ->{
                        Log.i(TAG, "observers ResponseState.Success: ${result.data?.name} ")

                        // do someThing logics..
                        binding.tv.text = "Success"

                        findNavController().safeNavigate(SplashFragmentDirections.actionSplashFragmentToHomeFragment("Hasan"))
                    }

                    is ResponseState.Loading -> {
                        Log.i(TAG, "observers ResponseState.Loading : ")
                        binding.tv.text = "Loading"
                    }

                    is ResponseState.Error -> {
                        Log.i(TAG, "observers ResponseState.Error: ")
                        val snackBar = Snackbar.make(binding.root, "ERROR", Snackbar.LENGTH_INDEFINITE)
                        snackBar.setAction("retry") {
                            viewModel.apiCall()
                        }
                        snackBar.show()
                        binding.tv.text = "Error"
                    }

                }
            }

        }

    }
}