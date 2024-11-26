package ir.ha.meproject.presentation.fragments.features.home

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

        binding.arguments.text = args.argument
    }


    override fun listeners() {
        super.listeners()

        binding.goToMoreFragment.singleClick {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToMoreFragment("Zahra"))
        }
    }

}