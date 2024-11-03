package ir.ha.meproject.presentation.features.fragments.temp3

import ir.ha.meproject.common.base.BaseFragment
import ir.ha.meproject.databinding.FragmentTemp3Binding
import ir.ha.meproject.presentation.features.fragments.temp2.Temp2FragmentArgs

class Temp3Fragment : BaseFragment<FragmentTemp3Binding>(FragmentTemp3Binding::inflate) {


    private lateinit var args : Temp3FragmentArgs

    override fun initializing() {
        super.initializing()
        args = Temp3FragmentArgs.fromBundle(requireArguments())
    }


    override fun uiConfig() {
        super.uiConfig()

        binding.argumentsTV.text = "Args : " + args.argument
    }


    override fun listeners() {
        super.listeners()
    }

}