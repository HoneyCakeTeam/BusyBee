package com.example.busybee.utils

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val bearerToken =
            "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJodHRwczovL3RoZS1jaGFuY2Uub3JnLyIsInN1YiI6IjNlZGY3YzAyLTgwYzUtNGQzMS04NTc1LTNiOTU1OTA3MzgwYyIsInRlYW1JZCI6ImVkMDFlODZmLWRlYWQtNGJhMC04NzhiLTY4YWVjOGE1MTU2NCIsImlzcyI6Imh0dHBzOi8vdGhlLWNoYW5jZS5vcmcvIiwiZXhwIjoxNjgxMzM4NTMzfQ.NrkzqfirrJdl6idTcZ76N_xjaIECBY-l6gVQbCbXqnA"
        val request = chain.request().newBuilder()
            .addHeader(AUTHORIZATION, bearerToken)
            .build()
        return chain.proceed(request)
    }

    companion object {
        private const val AUTHORIZATION = "Authorization"
    }
}