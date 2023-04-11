package com.example.busybee.data

import android.content.Context
import android.util.Base64
import com.example.busybee.BuildConfig
import com.example.busybee.data.models.LoginResponse
import com.example.busybee.data.models.SignUpResponse
import com.example.busybee.data.source.ConnectionBuilder
import com.example.busybee.data.source.executeWithCallbacks
import com.example.busybee.utils.AuthorizationInterceptor
import com.example.busybee.utils.Constant
import com.google.gson.reflect.TypeToken
import okhttp3.*

class Repository(private val context: Context) : RepositoryInterface {

    val client = OkHttpClient.Builder().apply {
        addInterceptor(ConnectionBuilder.logInterceptor)
        addInterceptor(AuthorizationInterceptor(context))
    }.build()
    override fun <T> logIn(
        userName: String, password: String, onSuccessCallback: (response: T) -> Unit,
        onFailureCallback: (error: Throwable) -> Unit
    ) {

        val logInClient =
            OkHttpClient.Builder().addInterceptor(ConnectionBuilder.logInterceptor).build()

        val request = Request.Builder()
            .url(Constant.loginUrl)
            .addHeader(
                "Authorization",
                "Basic " + Base64.encodeToString(
                    "${userName}:$password".toByteArray(),
                    Base64.NO_WRAP
                )
            )
            .build()

        val responseType = object : TypeToken<LoginResponse>() {}.type

        logInClient.executeWithCallbacks(
            request,
            responseType,
            onSuccessCallback,
            onFailureCallback
        )

    }

    override fun <T> signUp(
        userName: String,
        password: String,
        onSuccessCallback: (response: T) -> Unit,
        onFailureCallback: (error: Throwable) -> Unit
    ) {
        val signUpClient =
        OkHttpClient.Builder().addInterceptor(ConnectionBuilder.logInterceptor).build()

        val formBody =  FormBody.Builder()
            .add("username", userName)
            .add("password", password)
            .add("teamId",BuildConfig.API_KEY)
            .build()

        val request = Request.Builder()
            .url(Constant.registerUrl)
            .post(formBody)
            .build()



        val responseType = object : TypeToken<SignUpResponse>() {}.type

        signUpClient.executeWithCallbacks(
            request,
            responseType,
            onSuccessCallback,
            onFailureCallback
        )
    }

}