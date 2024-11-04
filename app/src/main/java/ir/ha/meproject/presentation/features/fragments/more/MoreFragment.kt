package ir.ha.meproject.presentation.features.fragments.more

import ir.ha.meproject.common.base.BaseFragment
import ir.ha.meproject.databinding.FragmentMoreBinding

class MoreFragment : BaseFragment<FragmentMoreBinding>(FragmentMoreBinding::inflate) {


    private lateinit var args : MoreFragmentArgs

    override fun initializing() {
        super.initializing()
        args = MoreFragmentArgs.fromBundle(requireArguments())
    }


    override fun uiConfig() {
        super.uiConfig()

        binding.argumentsTV.text = "Args : " + args.argument
    }


    override fun listeners() {
        super.listeners()
    }

}