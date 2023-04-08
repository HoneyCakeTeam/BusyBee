package com.example.busybee

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.busybee.data.models.LoginResponse
import com.example.busybee.data.source.execute
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
       // BuildConfig.API_KEY

        val httpUrl = HttpUrl.Builder()
            .scheme("https")
            .host("team-todo-62dmq.ondigitalocean.app")
            .addPathSegment("login")
            .addQueryParameter("username", "aziza_helmy")
            .addQueryParameter("password","123456789")
            .addQueryParameter("teamId","ed01e86f-dead-4ba0-878b-68aec8a51564")
            .build()
        val logInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }
        val client = OkHttpClient.Builder().addInterceptor(logInterceptor).build()
        val request = Request.Builder()
            .url(httpUrl)
            .build()
        client.execute<List<LoginResponse>>(request, { response ->
            Log.e("TAG", "onViewCreated:${request.body} ", )
            // handle successful response
        }, { error ->
            Log.e("TAG", "Error:$error ", )
            // handle error
        })

    
    }
}