package ir.ha.meproject.utility.util

import android.content.Context
import android.graphics.Typeface
import androidx.core.content.res.ResourcesCompat

object FontUtil {
    fun getTypeface(context: Context, fontResId: Int): Typeface? {
        return ResourcesCompat.getFont(context, fontResId)
    }
}