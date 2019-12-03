package by.android.base.manager

import android.content.Context
import android.net.ConnectivityManager

// Network manager
interface NetworkManager {
    fun isNetAvailable(): Boolean
}

class ImplNetworkManager(
      private val context: Context
) : NetworkManager {

    override fun isNetAvailable(): Boolean {
        val service = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val info = service.activeNetworkInfo
        return info != null && info.isConnectedOrConnecting
    }
}
