package by.sitko.restapp.manager

import android.content.Context
import android.os.Handler
import android.widget.Toast
import androidx.annotation.StringRes
import by.sitko.restapp.util.WHITESPACE

interface ToastManager {
    fun showMessage(@StringRes resId: Int)
    fun showMessage(vararg messages: String = emptyArray())
    fun showMessage(message: String?, resAlter: Int)

    class Impl(
          private val context: Context,
          private val handler: Handler,
          shouldUseLongTime: Boolean = false
    ) : ToastManager {

        private val showTime = if (shouldUseLongTime) Toast.LENGTH_LONG else Toast.LENGTH_SHORT

        override fun showMessage(resId: Int) {
            handler.post { Toast.makeText(context, context.getText(resId), showTime).show() }
        }

        override fun showMessage(vararg messages: String) {
            val stringBuilder = StringBuilder()
            for (item in messages) {
                stringBuilder.append(item).append(WHITESPACE)
            }

            handler.post {
                Toast.makeText(context, stringBuilder.toString().trim(), showTime).show()
            }
        }

        override fun showMessage(message: String?, resAlter: Int) {
            handler.post {
                val resultMessage = if (message == null) context.getText(resAlter) else message
                Toast.makeText(context, resultMessage, showTime).show()
            }
        }
    }

}


