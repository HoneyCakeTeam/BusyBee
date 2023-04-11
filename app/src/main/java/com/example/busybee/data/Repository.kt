package com.example.busybee.data

import android.content.Context
import android.util.Base64
import com.example.busybee.data.models.LoginResponse
import com.example.busybee.data.models.PersonalToDoListResponse
import com.example.busybee.data.models.TeamToDoListResponse
import com.example.busybee.data.source.ConnectionBuilder
import com.example.busybee.data.source.executeWithCallbacks
import com.example.busybee.utils.AuthorizationInterceptor
import com.example.busybee.utils.Constant
import com.google.gson.reflect.TypeToken
import okhttp3.OkHttpClient
import okhttp3.Request

class Repository(private val context: Context) : RepositoryInterface {

    private val client = OkHttpClient.Builder().apply {
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
            .url(Constant.LOGIN_URL)
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

    override fun <T> getAllTeamTasks(
        onSuccessCallback: (response: T) -> Unit,
        onFailureCallback: (error: Throwable) -> Unit
    ) {
        val request = Request.Builder()
            .url(Constant.TEAM_TODO_URL)
            .build()

        val responseType = object : TypeToken<TeamToDoListResponse>() {}.type

        client.executeWithCallbacks(
            request,
            responseType,
            onSuccessCallback,
            onFailureCallback
        )
    }

    override fun <T> createTeamToDo(
        title: String,
        description: String,
        assignee: String,
        onSuccessCallback: (response: T) -> Unit,
        onFailureCallback: (error: Throwable) -> Unit
    ) {
        val request = Request.Builder()
            .url(Constant.TEAM_TODO_URL)
            .build()

        val responseType = object :TypeToken<TeamToDoListResponse>() {}.type

        client.executeWithCallbacks(
            request,
            responseType,
            onSuccessCallback,
            onFailureCallback
        )
    }

    override fun <T> getPersonalTasks(
        onSuccessCallback: (response: T) -> Unit,
        onFailureCallback: (error: Throwable) -> Unit
    ) {
        val request = Request.Builder()
            .url(Constant.BASE_URL+Constant.PERSONAL_TASKS_END_POINT)
            .build()

        val responseType = object :TypeToken<PersonalToDoListResponse>(){}.type

        client.executeWithCallbacks(
            request,
            responseType,
            onSuccessCallback,
            onFailureCallback
        )

    }


}