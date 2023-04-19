package com.example.busybee.utils

import android.content.Context
import android.net.ConnectivityManager
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException


class ConnectivityInterceptor(private val context: Context) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        if (!isOnline(context)){
            throw NoConnectivityException("No Internet Connection")
        }

        val request = chain.request().newBuilder()
            .build()

        return chain.proceed(request)
    }

}

class NoConnectivityException(message: String) : IOException(message)


fun isOnline(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkInfo = connectivityManager.activeNetworkInfo
    return networkInfo != null && networkInfo.isConnected
}