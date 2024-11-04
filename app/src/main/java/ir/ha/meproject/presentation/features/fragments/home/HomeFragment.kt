package ir.ha.meproject.presentation.features.fragments.home

import androidx.navigation.fragment.findNavController
import ir.ha.meproject.databinding.FragmentHomeBinding
import ir.ha.meproject.common.base.BaseFragment
import ir.ha.meproject.common.extensions.singleClick

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private lateinit var args : HomeFragmentArgs

    override fun initializing() {
        super.initializing()
        args = HomeFragmentArgs.fromBundle(requireArguments())
    }


    override fun uiConfig() {
        super.uiConfig()

        binding.arguments.text = "Args : " + args.argument
    }


    override fun listeners() {
        super.listeners()

        binding.goToNextPage.singleClick {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToMoreFragment("Zahra"))
        }
    }

}