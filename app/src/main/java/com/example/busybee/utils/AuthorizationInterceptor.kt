package com.example.busybee.utils

import android.content.Context
import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        SharedPreferencesUtils.initPreferencesUtil(context)
        val bearerToken = "Bearer ${SharedPreferencesUtils.token}"
        Log.e("Token","Token : $bearerToken")
        val request = chain.request().newBuilder()
            .addHeader(AUTHORIZATION, bearerToken)
            .build()
        return chain.proceed(request)
    }

    companion object {
        private const val AUTHORIZATION = "Authorization"
    }
}