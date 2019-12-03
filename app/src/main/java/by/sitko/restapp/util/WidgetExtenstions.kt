package by.sitko.restapp.util

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import com.google.android.material.textfield.TextInputLayout

fun EditText.validate(validator: (String) -> Boolean, message: String) {
    this.doAfterTextChanged {
        this.error = if (validator(it)) null else message
    }
    this.error = if (validator(this.text.toString())) null else message
}

fun EditText.doAfterTextChanged(function: (String) -> Unit) {

    addTextChangedListener(
        object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                function.invoke(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
}

fun TextInputLayout.showError(messageError: String?) {
    isErrorEnabled = messageError != null
    error = messageError
}
