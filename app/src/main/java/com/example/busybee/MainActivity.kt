package com.example.busybee

import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.busybee.data.models.LoginRequest
import com.example.busybee.data.models.LoginResponse
import com.example.busybee.data.source.ConnectionBuilder.Companion.logInterceptor
import com.example.busybee.data.source.executeWithCallbacks
import com.example.busybee.utils.Constant.loginUrl
import com.google.gson.reflect.TypeToken
import okhttp3.OkHttpClient
import okhttp3.Request

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }
}