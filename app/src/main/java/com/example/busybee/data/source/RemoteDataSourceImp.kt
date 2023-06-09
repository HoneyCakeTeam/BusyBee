package com.example.busybee.data.source

import android.content.Context
import android.util.Base64
import com.example.busybee.BuildConfig
import com.example.busybee.data.models.*
import com.example.busybee.utils.AuthorizationInterceptor
import com.example.busybee.utils.Constant
import com.example.busybee.utils.executeWithCallbacks
import com.google.gson.reflect.TypeToken
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor

class RemoteDataSourceImp(private val context: Context) : RemoteDataSource {

    private val logInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BASIC
    }

    private val client = OkHttpClient.Builder().apply {
        addInterceptor(logInterceptor)
        addInterceptor(AuthorizationInterceptor(context))
    }.build()

    override fun logIn(
        userName: String,
        password: String,
        onSuccessCallback: (response: BaseResponse<LoginResponseValue>) -> Unit,
        onFailureCallback: (error: Throwable) -> Unit
    ) {

        val logInClient =
            OkHttpClient.Builder().addInterceptor(logInterceptor).build()

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

        val responseType = object : TypeToken<BaseResponse<LoginResponseValue>>() {}.type

        logInClient.executeWithCallbacks(
            request,
            responseType,
            onSuccessCallback,
            onFailureCallback
        )

    }

    override fun signUp(
        userName: String,
        password: String,
        onSuccessCallback: (response: BaseResponse<SignUpResponseValue>) -> Unit,
        onFailureCallback: (error: Throwable) -> Unit
    ) {
        val signUpClient =
            OkHttpClient.Builder().addInterceptor(logInterceptor).build()

        val formBody = FormBody.Builder()
            .add("username", userName)
            .add("password", password)
            .add("teamId", BuildConfig.API_KEY)
            .build()

        val request = Request.Builder()
            .url(Constant.REGISTER_URL)
            .post(formBody)
            .build()


        val responseType = object : TypeToken<BaseResponse<SignUpResponseValue>>() {}.type

        signUpClient.executeWithCallbacks(
            request,
            responseType,
            onSuccessCallback,
            onFailureCallback
        )
    }

    override fun getAllTeamTasks(
        onSuccessCallback: (response: BaseResponse<List<TeamToDo>>) -> Unit,
        onFailureCallback: (error: Throwable) -> Unit
    ) {
        val request = Request.Builder()
            .url(Constant.TEAM_TODO_URL)
            .build()

        val responseType = object : TypeToken<BaseResponse<List<TeamToDo>>>() {}.type

        client.executeWithCallbacks(
            request,
            responseType,
            onSuccessCallback,
            onFailureCallback
        )
    }

    override fun createTeamToDo(
        title: String,
        description: String,
        assignee: String,
        onSuccessCallback: (response: BaseResponse<TeamToDo>) -> Unit,
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

        val responseType = object : TypeToken<BaseResponse<TeamToDo>>() {}.type

        client.executeWithCallbacks(
            request,
            responseType,
            onSuccessCallback,
            onFailureCallback
        )
    }

    override fun createPersonalToDo(
        title: String,
        description: String,
        onSuccessCallback: (response: BaseResponse<PersonalToDo>) -> Unit,
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

        val responseType = object : TypeToken<BaseResponse<PersonalToDo>>() {}.type

        client.executeWithCallbacks(
            request,
            responseType,
            onSuccessCallback,
            onFailureCallback
        )
    }

    override fun getPersonalTasks(
        onSuccessCallback: (response: BaseResponse<List<PersonalToDo>>) -> Unit,
        onFailureCallback: (error: Throwable) -> Unit
    ) {
        val request = Request.Builder()
            .url(Constant.PERSONAL_TODO_URL)
            .build()

        val responseType = object : TypeToken<BaseResponse<List<PersonalToDo>>>() {}.type

        client.executeWithCallbacks(
            request,
            responseType,
            onSuccessCallback,
            onFailureCallback
        )

    }

    override fun updateTasksPersonalStatus(
        idTask: String,
        status: Int,
        onSuccessCallback: (response: BaseResponse<String>) -> Unit,
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

        val responseType = object : TypeToken<BaseResponse<String>>() {}.type

        client.executeWithCallbacks(
            request,
            responseType,
            onSuccessCallback,
            onFailureCallback
        )

    }

    override fun updateTasksTeamStatus(
        idTask: String,
        status: Int,
        onSuccessCallback: (response: BaseResponse<String>) -> Unit,
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

        val responseType = object : TypeToken<BaseResponse<String>>() {}.type
        client.executeWithCallbacks(request, responseType, onSuccessCallback, onFailureCallback)

    }

}