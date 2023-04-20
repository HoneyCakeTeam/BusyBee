package com.example.busybee.utils

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.example.busybee.ui.MainActivity
import com.example.busybee.utils.sharedpreference.SharedPreferencesUtils
import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        SharedPreferencesUtils(context)
        //val tokenTestExpired =
            "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJodHRwczovL3RoZS1jaGFuY2Uub3JnLyIsInN1YiI6IjNlYWRkOGM5LTFkMWUtNGM1Ni04MTE2LWM3ZDk0YmQ1ZTQ5ZSIsInRlYW1JZCI6ImVkMDFlODZmLWRlYWQtNGJhMC04NzhiLTY4YWVjOGE1MTU2NCIsImlzcyI6Imh0dHBzOi8vdGhlLWNoYW5jZS5vcmcvIiwiZXhwIjoxNjgxNDIzMTEwfQ.zhSBne1c2SOM7tvkd2hB4ZUqpkWrvjhA9Cv7CloidaQ"
        val bearerToken = "Bearer ${SharedPreferencesUtils(context).token}"
        val request = chain.request().newBuilder()
            .addHeader(AUTHORIZATION, bearerToken)
            .build()

        val response = chain.proceed(request)

        if (response.code == 401) {
            val intent = Intent(context, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            val bundle = Bundle()
            bundle.putString(Constant.FRAGMENT_KEY, "myFragmentTag")
            intent.putExtra(Constant.BUNDLE_TASK, bundle)
            context.startActivity(intent)
            response.close()
        }
        return response
    }

    companion object {
        private const val AUTHORIZATION = "Authorization"
    }

}