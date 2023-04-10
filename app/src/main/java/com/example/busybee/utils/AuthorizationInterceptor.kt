package com.example.busybee.utils

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val bearerToken = "Bearer ${SharedPreferencesUtils.token}"
        val request = chain.request().newBuilder()
            .addHeader(AUTHORIZATION, "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJodHRwczovL3RoZS1jaGFuY2Uub3JnLyIsInN1YiI6ImVkN2RlMDgyLTU3ODYtNGE0ZS04NzhiLWU4ZWRhOTM0ZmExMCIsInRlYW1JZCI6ImVkMDFlODZmLWRlYWQtNGJhMC04NzhiLTY4YWVjOGE1MTU2NCIsImlzcyI6Imh0dHBzOi8vdGhlLWNoYW5jZS5vcmcvIiwiZXhwIjoxNjgxMjM2MjEwfQ.p4N7kgWx_a8KO9sIWoE8_rz6p8Aj1BHQQ5owig3c9oc")
            .build()
        return chain.proceed(request)
    }

    companion object {
        private const val AUTHORIZATION = "Authorization"
    }
}