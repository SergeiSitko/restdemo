package by.android.base.manager

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat

interface ResourceManager {
    fun getString(@StringRes stringRes: Int): String
    fun getStringArray(stringArrRes: Int): Array<String>
    fun getColor(colorRes: Int): Int
    fun getDrawable(@DrawableRes stringArrRes: Int): Drawable?
    fun getDimen(dimenRes: Int): Float

    class ImpResourceManager(
          private val context: Context
    ) : ResourceManager {
        override fun getString(stringRes: Int) = context.getString(stringRes)
        override fun getStringArray(stringArrRes: Int) = context.resources.getStringArray(stringArrRes) as Array<String>
        override fun getColor(colorRes: Int) = ContextCompat.getColor(context, colorRes)
        override fun getDrawable(stringArrRes: Int) = ContextCompat.getDrawable(context, stringArrRes)
        override fun getDimen(dimenRes: Int) = context.resources.getDimension(dimenRes)
    }
}
