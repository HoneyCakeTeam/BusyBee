package com.example.busybee

import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.busybee.data.models.LoginResponse
import com.example.busybee.data.source.executeWithCallbacks
import com.google.gson.reflect.TypeToken
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // BuildConfig.API_KEY
        val username = "nourelden515"
        val password = "123456789"

        val httpUrl = HttpUrl.Builder()
            .scheme("https")
            .host("team-todo-62dmq.ondigitalocean.app")
            .addPathSegment("login")
            .build()

        val logInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }

        val client = OkHttpClient.Builder().addInterceptor(logInterceptor).build()

        val request = Request.Builder()
            .url(httpUrl)
            .addHeader(
                "Authorization",
                "Basic " + Base64.encodeToString(
                    "$username:$password".toByteArray(),
                    Base64.NO_WRAP
                )
            )
            .build()

        val responseType = object : TypeToken<LoginResponse>() {}.type
        client.executeWithCallbacks<LoginResponse>(request, responseType, {
                response -> Log.e("TAG", "onViewCreated:${response} ")

        }, { error -> Log.e("TAG", "Error:$error ")

        })
    }
}