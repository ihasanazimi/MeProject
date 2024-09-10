package ir.ha.meproject.utility.ui.customViews.temp

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.google.android.material.button.MaterialButton
import ir.ha.meproject.R
import ir.ha.meproject.databinding.LayoutLoadingButtonBinding

class LoadingButton(context: Context, attrs: AttributeSet?) : RelativeLayout(context, attrs) {

    private val TAG = LoadingButton::class.java.simpleName
    private var binding: LayoutLoadingButtonBinding
    private var buttonTitle: String
    private var loadingTitle: String
    private var textColor = -1

    init {
        // Inflate the layout
        binding = LayoutLoadingButtonBinding.inflate(LayoutInflater.from(context), this, true)

        // Make sure the custom view itself respects width/height passed from the layout
        val layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        this.layoutParams = layoutParams

        // Load attributes
        val attr = context.theme.obtainStyledAttributes(attrs, R.styleable.LoadingButton, 0, 0)
            try {
                // Title and Loading Title
                buttonTitle = attr.getString(R.styleable.LoadingButton_loadingBtnTitle) ?: "تایید"
                loadingTitle = attr.getString(R.styleable.LoadingButton_loadingBtnLoadingTitle) ?: "کمی صبر کنید.."

                // Background Tint
                val backgroundColor = attr.getResourceId(R.styleable.LoadingButton_loadingBtnBackgroundColor, -1)
                if (backgroundColor != -1) {
                    backgroundTint(backgroundColor)
                }

                // Text Color
                textColor = attr.getColor(R.styleable.LoadingButton_loadingBtnTextColor, ContextCompat.getColor(context,R.color.white))
                if (textColor != -1) {
                    textColor(textColor)
                }

                // Icon
                val iconResId = attr.getResourceId(R.styleable.LoadingButton_loadingBtnIcon, -1)
                if (iconResId != -1) {
                    binding.btn.setIconResource(iconResId)
                }

                // Icon Tint
                val iconTint = attr.getResourceId(R.styleable.LoadingButton_loadingBtnIconTint, -1)
                if (iconTint != -1) {
                    binding.btn.iconTint = ContextCompat.getColorStateList(context, iconTint)
                }

                // Icon Size
                val iconSize = attr.getDimension(R.styleable.LoadingButton_loadingBtnIconSize, -1f)
                if (iconSize != -1f) {
                    binding.btn.iconSize = iconSize.toInt()
                }

                // Icon Padding
                val iconPadding = attr.getDimension(R.styleable.LoadingButton_loadingBtnIconPadding, -1f)
                if (iconPadding != -1f) {
                    binding.btn.iconPadding = iconPadding.toInt()
                }

                // Button Gravity
                val buttonGravity = attr.getInt(R.styleable.LoadingButton_loadingBtnGravity, 17)
                binding.btn.gravity = buttonGravity

                // Text Gravity
                val textGravity = attr.getInt(R.styleable.LoadingButton_loadingBtnTextGravity, 17)
                binding.btn.textAlignment = textGravity

                // Icon Gravity
                val iconGravity = attr.getInt(R.styleable.LoadingButton_loadingBtnGravity, 3)
                if (iconGravity == 3) {
                    binding.btn.iconGravity = MaterialButton.ICON_GRAVITY_START
                } else {
                    binding.btn.iconGravity = MaterialButton.ICON_GRAVITY_END
                }

                // Set initial text
                title(buttonTitle,loadingTitle)

            } finally {
                attr.recycle()
            }
    }

    // Methods to update attributes programmatically
    fun title(buttonTitle: String? = null, loadingTitle: String? = null) {
        this.buttonTitle = buttonTitle ?: this.buttonTitle
        this.loadingTitle = loadingTitle ?: this.loadingTitle
        binding.btn.text = this.buttonTitle
    }

    fun backgroundTint(color: Int) {
        binding.btn.setBackgroundColor(ContextCompat.getColor(context, color))
    }

    fun textColor(color: Int) {
        binding.btn.setTextColor(ColorStateList.valueOf(color))
    }

    fun showLoading(show: Boolean) {
        binding.progressBar.isVisible = show
        if (show) {
            binding.btn.text = /*loadingTitle*/ ""
            binding.btn.isEnabled = false
            textColor(ContextCompat.getColor(context,R.color.gray))
        } else {
            binding.btn.text = buttonTitle
            binding.btn.isEnabled = true
            textColor(textColor)
        }
    }

    fun clickListener(callback: (() -> Unit)? = null) {
        binding.btn.setOnClickListener { callback?.invoke() }
    }




}
