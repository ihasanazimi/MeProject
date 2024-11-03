package ir.ha.meproject.presentation.features.fragments.temp2

import androidx.navigation.fragment.findNavController
import ir.ha.meproject.databinding.FragmentTemp2Binding
import ir.ha.meproject.common.base.BaseFragment
import ir.ha.meproject.common.extensions.singleClick

class Temp2Fragment : BaseFragment<FragmentTemp2Binding>(FragmentTemp2Binding::inflate) {

    private lateinit var args : Temp2FragmentArgs

    override fun initializing() {
        super.initializing()
        args = Temp2FragmentArgs.fromBundle(requireArguments())
    }


    override fun uiConfig() {
        super.uiConfig()

        binding.arguments.text = "Args : " + args.argument
    }


    override fun listeners() {
        super.listeners()

        binding.goToNextPage.singleClick {
            findNavController().navigate(Temp2FragmentDirections.actionTemp2FragmentToTemp3Fragment("Zahra"))
        }
    }

}