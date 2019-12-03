package by.zmitrocc.shop.manager

import android.content.Context
import by.sitko.restapp.util.EMPTY

private const val USER_DATA = "USER_DATA"
private const val KEY_TOKEN = "KEY_TOKEN"

private const val valid_email = "hello@karta.com"
private const val valid_password = "hello@karta.com"

interface PasswordManager {
    fun isUserLogged(): Boolean
    fun saveToken(password: String)
    fun clearToken()
    fun getToken(): String
    fun validateMailAndPassword(mail: String, password: String): Boolean

    class Impl(context: Context) : PasswordManager {

        private val shar by lazy { context.getSharedPreferences(USER_DATA, Context.MODE_PRIVATE) }

        override fun isUserLogged() = shar.getString(KEY_TOKEN, null) != null

        override fun saveToken(password: String) {
            shar.edit().putString(KEY_TOKEN, password).apply()
        }

        override fun getToken() = shar.getString(KEY_TOKEN, EMPTY) ?: EMPTY

        override fun validateMailAndPassword(mail: String, password: String): Boolean =
              mail == valid_email && password == valid_password

        override fun clearToken() {
            shar.edit().clear().apply()
        }
    }
}
