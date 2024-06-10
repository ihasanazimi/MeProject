package ir.ha.meproject.ui.fragments.temp1

import android.graphics.Color
import ir.ha.meproject.R
import ir.ha.meproject.databinding.FragmentTemp1Binding
import ir.ha.meproject.utility.base.BaseFragment
import ir.ha.meproject.utility.extensions.singleClick

class Temp1Fragment : BaseFragment<FragmentTemp1Binding>(FragmentTemp1Binding::inflate) {

    override fun initializing() {
        super.initializing()


        binding.progress.setProgress(64,true)


        binding.add.singleClick {
            binding.progress.addProgress()
        }

        binding.low.singleClick {
            binding.progress.lowProgress()
        }

    }


    override fun uiConfig() {
        super.uiConfig()
    }


    override fun listeners() {
        super.listeners()
    }



}