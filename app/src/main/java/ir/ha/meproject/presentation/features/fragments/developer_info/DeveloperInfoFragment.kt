package ir.ha.meproject.presentation.features.fragments.developer_info

import android.util.Log
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import ir.ha.meproject.R
import ir.ha.meproject.common.base.BaseFragment
import ir.ha.meproject.common.extensions.withNotNull
import ir.ha.meproject.databinding.FragmentDeveloperInfoBinding
import ir.ha.meproject.model.data.developer_info.DeveloperInfo
import kotlinx.coroutines.launch


@AndroidEntryPoint
class DeveloperInfoFragment  : BaseFragment<FragmentDeveloperInfoBinding>(FragmentDeveloperInfoBinding::inflate) {

    private val viewModel : DeveloperInfoFragmentVM by viewModels()
    private val sendRequestByCoroutines = true

    override fun initializing() {
        super.initializing()

        if (sendRequestByCoroutines){
            viewModel.getDeveloperInfoByKotlinCoroutines()
        }else {
            viewModel.getDeveloperInfoByRxKotlin()
        }

    }

    override fun observers() {
        super.observers()

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.developerInfoForKotlinCoroutines.collect{
                Log.i(TAG, "DATA received By KotlinCoroutines at this time -> ${System.currentTimeMillis()}")
                updateUi(it)
            }
        }

        viewModel.developerInfoForRxAndroid.observe(viewLifecycleOwner){
            Log.i(TAG, "DATA received By RxAndroid at this time -> ${System.currentTimeMillis()}")
            updateUi(it)
        }

        viewModel.errorMessage.observe(viewLifecycleOwner){
            Log.i(TAG, "observers - errorMessage : $it")
            showErrorMessage(it)
        }

        viewModel.showLoading.observe(viewLifecycleOwner){
            Log.i(TAG, "observers - showLoading : $it")
            binding.loadingBar.root.isVisible = it
        }


    }

    private fun updateUi(developerInfo: DeveloperInfo) {
        developerInfo.withNotNull {
            loadProfileImage()
            binding.profleFullNameTV.text = "Mr." + it.firstName.plus(" ").plus(it.lastName)
            binding.jobTitleTV.text = it.jobTitle + " at " + it.resume.organizes.first().organizeName
            showMessage("done!")
        }
    }

    private fun loadProfileImage() = Glide.with(requireContext())
        .load("https://img.freepik.com/free-psd/3d-illustration-human-avatar-profile_23-2150671142.jpg")
        .placeholder(R.drawable.loading)
        .error(R.drawable.ic_failed)
        .into(binding.profileProfileIV)


}