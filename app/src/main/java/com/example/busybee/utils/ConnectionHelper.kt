package com.example.busybee.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.util.Log
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException
import java.lang.reflect.Type

/**
 * Created by Aziza Helmy on 4/7/2023.
*/
fun <T> OkHttpClient.executeWithCallbacks(
    request: Request,
    responseType: Type,
    onSuccessCallback: (response: T) -> Unit,
    onFailureCallback: (error: Throwable) -> Unit): Call {
    val call = newCall(request)
    val callback = object : Callback {
        override fun onResponse(call: Call, response: Response) {
            if (response.isSuccessful) {
                val responseBody = response.body?.string()
                val gson = Gson()
                val result = gson.fromJson<T>(responseBody, responseType)
                onSuccessCallback(result)
            } else {
                onFailureCallback(Throwable("$response"))
            }
        }

        override fun onFailure(call: Call, e: IOException) {
            onFailureCallback(e)
        }
    }
    call.enqueue(callback)
    return call
}

fun Fragment.isOnline(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val capabilities =
        connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
    if (capabilities != null) {
        if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
            Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
            return true
        } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
            Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
            return true
        } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
            Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
            return true
        }
    }
    return false
}

fun Fragment.registerConnectivityNetworkMonitor(
    context: Context
) {
    if (context != null) {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val builder = NetworkRequest.Builder()
        connectivityManager.registerNetworkCallback(builder.build(),
            object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    if (activity != null) {
                        Log.e("TAG", "Internet Available: ")
                    }
                }

                override fun onLost(network: Network) {
                    super.onLost(network)
                    Log.e("TAG", "Internet Lost: ")
                }
            }
        )
    }

}


