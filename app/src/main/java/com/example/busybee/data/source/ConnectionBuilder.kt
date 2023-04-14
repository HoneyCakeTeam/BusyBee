package com.example.busybee.data.source

import com.google.gson.Gson
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
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

