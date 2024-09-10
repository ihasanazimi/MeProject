package ir.ha.meproject.utility.ui.customViews

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import ir.ha.meproject.databinding.LayoutLoadingButtonBinding

class LoadingButton(context: Context, attrs: AttributeSet?) : RelativeLayout(context, attrs) {

    private val TAG = LoadingButton::class.java.simpleName
    private var binding : LayoutLoadingButtonBinding
    private var buttonTitle = "Confirm/OK"
    private var loadingTitle = "Please Waite ... "

    init {
        binding = LayoutLoadingButtonBinding.inflate(LayoutInflater.from(context),this,true)
        binding.btn.text = buttonTitle
    }


    fun title(buttonTitle : String?=null , loadingTitle : String?=null){
        Log.i(TAG, "buttonTitle: $buttonTitle   |   loadingTitle : $loadingTitle")
        this.buttonTitle = buttonTitle?:this.buttonTitle
        this.loadingTitle = loadingTitle?:this.loadingTitle
        binding.btn.text = this.buttonTitle
    }

    fun backgroundTint(color : Int){
        Log.i(TAG, "backgroundTint: ")
        binding.btn.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(context,color))
    }

    fun textColor(color : Int){
        Log.i(TAG, "textColor: ")
        binding.btn.setTextColor(ContextCompat.getColor(context,color))
    }

    fun showLoading(show : Boolean){
        Log.i(TAG, "showLoading: $show ")
        if (show) {
            binding.btn.text = loadingTitle
            binding.btn.isEnabled = false
            binding.btn.isClickable = false
            binding.btn.isFocusable = false
        }
        else {
            binding.btn.text = buttonTitle
            binding.btn.isEnabled = true
            binding.btn.isClickable = true
            binding.btn.isFocusable = true
        }
    }

    fun enable(enable : Boolean){
        Log.i(TAG, "enable: $enable")
        binding.btn.isEnabled = enable
        binding.btn.isClickable = enable
        binding.btn.isFocusable = enable
    }


    fun clickListener(callback: (() -> Unit)? = null){
        binding.btn.setOnClickListener {
            Log.i(TAG, "clickListener: ")
            callback?.invoke()
        }
    }

}