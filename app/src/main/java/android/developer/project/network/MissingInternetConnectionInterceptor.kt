package android.developer.project.network

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import kotlin.jvm.Throws

class MissingInternetConnectionInterceptor(private val context: Context) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        if (!NetworkConnectivityHelper.isConnected(context)) {
            throw MissingInternetConnectionException()
        }
        return chain.proceed(request)
    }
}