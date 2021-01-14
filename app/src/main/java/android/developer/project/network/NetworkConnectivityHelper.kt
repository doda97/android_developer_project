package android.developer.project.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

class NetworkConnectivityHelper {
    companion object{
        fun isConnected(context: Context): Boolean {
            return (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).run {
                getNetworkCapabilities(activeNetwork)?.run {
                    hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                            || hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                            || hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
                } ?: false
            }
        }
    }
}