package com.example.busybee

import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.busybee.data.models.LoginRequest
import com.example.busybee.data.models.LoginResponse
import com.example.busybee.data.source.ConnectionBuilder.Companion.logInterceptor
import com.example.busybee.data.source.executeWithCallbacks
import com.example.busybee.util.Constant.baseUrl
import com.example.busybee.util.Constant.loginUrl
import com.google.gson.reflect.TypeToken
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val username = "nourelden515"
        val password = "123456789"
        val teamId=BuildConfig.API_KEY

        val loginRequest=LoginRequest(username,password,teamId)

        val client = OkHttpClient.Builder().addInterceptor(logInterceptor).build()


        val request = Request.Builder()
            .url(loginUrl)
            .addHeader(
                "Authorization",
                "Basic " + Base64.encodeToString(
                    "$username:$password".toByteArray(),
                    Base64.NO_WRAP))
            .build()

        val responseType = object : TypeToken<LoginResponse>() {}.type

        client.executeWithCallbacks<LoginResponse>(request, responseType, {
                response -> Log.e("TAG", "onViewCreated:${response} ")

        }, { error -> Log.e("TAG", "Error:$error ")

        })
    }
}