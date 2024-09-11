package ir.ha.meproject.common.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import ir.ha.meproject.R
import ir.ha.meproject.presentation.MainActivity
import ir.ha.meproject.common.extensions.hideKeyboard
import ir.ha.meproject.common.more.SnackBarHelper

abstract class BaseFragment<VB : ViewBinding>(
    private val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB
) : Fragment() {

    val TAG = this::class.java.simpleName
    val parentActivity by lazy { (requireActivity() as MainActivity) }

    private var _binding: VB? = null
    protected val binding: VB
        get() = _binding ?: throw IllegalStateException("Fragment view not created yet.")


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = bindingInflater.invoke(inflater, container, false)
        Log.i(TAG, "onCreateView: ")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i(TAG, "onViewCreated: ")
        hideKeyboard(view)
        initializing()
        uiConfig()
        listeners()
        observers()
    }

    open fun initializing(){
        Log.i(TAG, "initializing: ")
    }

    open fun uiConfig(){
        Log.i(TAG, "uiConfig: ")
    }
    open fun observers(){
        Log.i(TAG, "observers: ")
    }
    open fun listeners(){
        Log.i(TAG, "listeners: ")
    }

    fun showErrorMessage(message: String) {
        Log.e(TAG, "showErrorMessage - $message")
        SnackBarHelper.showSnackBar(
            requireActivity() , message, R.drawable.ic_info
        )
    }

    fun showMessage(message: String, icon: Int = R.drawable.ic_done) {
        Log.e("TAG", "showMessage: ")
        SnackBarHelper.showSnackBar(
          requireActivity(), message, icon
        )
    }

    fun onBackPressed(){
        Log.i(TAG, "onBackPressed: ")
        parentActivity.onBackPressedDispatcher.onBackPressed()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "onDestroy: ")
        _binding = null
    }

}