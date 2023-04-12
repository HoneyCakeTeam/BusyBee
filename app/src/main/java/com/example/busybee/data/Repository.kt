package com.example.busybee.data

import android.content.Context
import android.util.Base64
import com.example.busybee.BuildConfig
import com.example.busybee.data.models.*
import com.example.busybee.data.source.ConnectionBuilder
import com.example.busybee.data.source.executeWithCallbacks
import com.example.busybee.utils.AuthorizationInterceptor
import com.example.busybee.utils.Constant
import com.example.busybee.utils.SharedPreferencesUtils
import com.google.gson.reflect.TypeToken
import okhttp3.FormBody
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

    override fun <T> signUp(
        userName: String,
        password: String,
        onSuccessCallback: (response: T) -> Unit,
        onFailureCallback: (error: Throwable) -> Unit
    ) {
        val signUpClient =
            OkHttpClient.Builder().addInterceptor(ConnectionBuilder.logInterceptor).build()

        val formBody = FormBody.Builder()
            .add("username", userName)
            .add("password", password)
            .add("teamId", BuildConfig.API_KEY)
            .build()

        val request = Request.Builder()
            .url(Constant.REGISTER_URL)
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
        val formBody = FormBody.Builder()
            .add("title", title)
            .add("description", description)
            .add("assignee", assignee)
            .build()

        val request = Request.Builder()
            .url(Constant.TEAM_TODO_URL)
            .post(formBody)
            .build()

        val responseType = object : TypeToken<TeamCreateToDoResponse>() {}.type

        client.executeWithCallbacks(
            request,
            responseType,
            onSuccessCallback,
            onFailureCallback
        )
    }

    override fun <T> createPersonalToDo(
        title: String,
        description: String,
        onSuccessCallback: (response: T) -> Unit,
        onFailureCallback: (error: Throwable) -> Unit
    ) {
        val formBody = FormBody.Builder()
            .add("title", title)
            .add("description", description)
            .build()

        val request = Request.Builder()
            .url(Constant.PERSONAL_TODO_URL)
            .post(formBody)
            .build()

        val responseType = object : TypeToken<PersonalCreateToDoResponse>() {}.type

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
            .url(Constant.PERSONAL_TODO_URL)
            .build()

        val responseType = object : TypeToken<PersonalToDoListResponse>() {}.type

        client.executeWithCallbacks(
            request,
            responseType,
            onSuccessCallback,
            onFailureCallback
        )

    }

    override fun <T> updateTasksPersonalStatus(
        idTask: String,
        status: Int,
        onSuccessCallback: (response: T) -> Unit,
        onFailureCallback: (error: Throwable) -> Unit,
    ) {

        val formBody = FormBody.Builder()
            .add("id", idTask)
            .add("status", status.toString())
            .build()

        val request = Request.Builder()
            .url(Constant.PERSONAL_TODO_URL)
            .put(formBody)
            .build()

        val responseType = object : TypeToken<PersonalUpdateStatusResponse>() {}.type

        client.executeWithCallbacks(
            request,
            responseType,
            onSuccessCallback,
            onFailureCallback
        )

    }


    override fun <T> updateTasksTeamStatus(
        idTask: String,
        status: Int,
        onSuccessCallback: (response: T) -> Unit,
        onFailureCallback: (error: Throwable) -> Unit
    ) {


        val formBody = FormBody.Builder()
            .add("id", idTask)
            .add("status", status.toString())
            .build()

        val request = Request.Builder()
            .url(Constant.TEAM_TODO_URL)
            .put(formBody)
            .build()

        val responseType = object : TypeToken<TeamUpdateStatusResponse>() {}.type
        client.executeWithCallbacks(request, responseType, onSuccessCallback, onFailureCallback)

    }

    override fun saveTokenInShared(token: String) {
        SharedPreferencesUtils.token = token
    }

    override fun saveExpirationDateInShared(expirationDate: String) {
        SharedPreferencesUtils.initPreferencesUtil(context)
        SharedPreferencesUtils.expirationDate = expirationDate
    }


}