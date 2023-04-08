package com.example.busybee

import android.content.res.Configuration
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.busybee.data.Repository
import com.example.busybee.data.models.LoginRequest
import com.example.busybee.data.models.LoginResponse
import com.example.busybee.data.source.ConnectionBuilder
import com.example.busybee.data.source.executeWithCallbacks
import com.example.busybee.utils.Constant
import com.google.gson.reflect.TypeToken
import okhttp3.OkHttpClient
import okhttp3.Request

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        statusBarTheme()


        //=================4 Testing================
        val username = "nourelden515"
        val password = "123456789"
        val teamId=BuildConfig.API_KEY

        /*
        val loginRequest= LoginRequest(username,password,teamId)

        val client = OkHttpClient.Builder().addInterceptor(ConnectionBuilder.logInterceptor).build()


        val request = Request.Builder()
            .url(Constant.loginUrl)
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
        //==================================
*/


        val repo = Repository()
        repo.logIn<LoginResponse>(username , password , {Log.e("TAG", "SUCSSESS:$it ")} , {Log.e("TAG", "Error:$it ")} )


    }


    @Suppress("DEPRECATION")
    private fun statusBarTheme() {
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        window.statusBarColor = Color.TRANSPARENT

        if (isDarkTheme()) {
            window.decorView.systemUiVisibility =
                window.decorView.systemUiVisibility and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
        } else {
            window.decorView.systemUiVisibility =
                window.decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val decorView = window.decorView
            decorView.systemUiVisibility =
                decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
        }
    }

    private fun isDarkTheme(): Boolean {
        val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        return currentNightMode == Configuration.UI_MODE_NIGHT_YES
    }
}