package by.sitko.restapp.util

import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

fun getViewVisibility(shouldShow: Boolean) = if (shouldShow) View.VISIBLE else View.GONE

fun Fragment.showToast(message: String) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
}

fun Fragment.showToast(@StringRes res: Int) {
    Toast.makeText(requireContext(), resources.getString(res), Toast.LENGTH_SHORT).show()
}