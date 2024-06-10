package ir.ha.meproject.ui.fragments.temp1

import androidx.lifecycle.lifecycleScope
import ir.ha.meproject.databinding.FragmentTemp1Binding
import ir.ha.meproject.utility.base.BaseFragment
import ir.ha.meproject.utility.extensions.showToast
import ir.ha.meproject.utility.ui.customViews.temp.CircleProgress
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class Temp1Fragment : BaseFragment<FragmentTemp1Binding>(FragmentTemp1Binding::inflate) {

    override fun initializing() {
        super.initializing()


        binding.progress.setProgress(64,true)
        binding.progress.setOnProgressChangedListener(object : CircleProgress.OnProgressChangedListener{
            override fun onProgressChanged(progress: Int) {
                showToast(requireContext(),progress.toString())
            }
        })

    }


    override fun uiConfig() {
        super.uiConfig()
    }


    override fun listeners() {
        super.listeners()
    }



}