package by.sitko.restapp.util

import android.util.Log.*

inline fun Any.logDebug(message: String) = d(javaClass.simpleName, message)

inline fun Any.logInfo(message: String) = i(javaClass.simpleName, message)
inline fun Any.logWarning(message: String) = w(javaClass.simpleName, message)
inline fun Any.logError(throwable: Throwable) = e(javaClass.simpleName, throwable.message)