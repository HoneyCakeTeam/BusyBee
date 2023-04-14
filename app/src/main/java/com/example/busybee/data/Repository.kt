package com.example.busybee.data

import android.content.Context
import com.example.busybee.data.source.RemoteDataSourceInterface
import com.example.busybee.utils.SharedPreferencesUtils

class Repository(private val remoteDataSource: RemoteDataSourceInterface,
                 private val sharedPreferences: SharedPreferencesUtils ,
                 val context: Context) : RepositoryInterface {



    override fun <T> logIn(
        userName: String, password: String, onSuccessCallback: (response: T) -> Unit,
        onFailureCallback: (error: Throwable) -> Unit
    ) {
        remoteDataSource.logIn<T>(userName, password , onSuccessCallback , onFailureCallback)

    }

    override fun <T> signUp(
        userName: String,
        password: String,
        onSuccessCallback: (response: T) -> Unit,
        onFailureCallback: (error: Throwable) -> Unit
    ) {
       remoteDataSource.signUp<T>(userName, password, onSuccessCallback, onFailureCallback)
    }

    override fun <T> getAllTeamTasks(
        onSuccessCallback: (response: T) -> Unit,
        onFailureCallback: (error: Throwable) -> Unit
    ) {
        remoteDataSource.getAllTeamTasks<T>(onSuccessCallback, onFailureCallback)
    }

    override fun <T> createTeamToDo(
        title: String,
        description: String,
        assignee: String,
        onSuccessCallback: (response: T) -> Unit,
        onFailureCallback: (error: Throwable) -> Unit
    ) {
       remoteDataSource.createTeamToDo<T>(title, description, assignee, onSuccessCallback, onFailureCallback)
    }

    override fun <T> createPersonalToDo(
        title: String,
        description: String,
        onSuccessCallback: (response: T) -> Unit,
        onFailureCallback: (error: Throwable) -> Unit
    ) {
        remoteDataSource.createPersonalToDo<T>(title, description, onSuccessCallback, onFailureCallback)
    }

    override fun <T> getPersonalTasks(
        onSuccessCallback: (response: T) -> Unit,
        onFailureCallback: (error: Throwable) -> Unit
    ) {
        remoteDataSource.getPersonalTasks<T>(onSuccessCallback, onFailureCallback)

    }

    override fun <T> updateTasksPersonalStatus(
        idTask: String,
        status: Int,
        onSuccessCallback: (response: T) -> Unit,
        onFailureCallback: (error: Throwable) -> Unit,
    ) {
        remoteDataSource.updateTasksPersonalStatus<T>(idTask, status,
            onSuccessCallback, onFailureCallback)
    }


    override fun <T> updateTasksTeamStatus(
        idTask: String,
        status: Int,
        onSuccessCallback: (response: T) -> Unit,
        onFailureCallback: (error: Throwable) -> Unit
    ) {

        remoteDataSource.updateTasksTeamStatus<T>(idTask, status, onSuccessCallback, onFailureCallback)
    }

    override fun saveTokenInShared(token: String?) {
        sharedPreferences.initPreferencesUtil(context)
        sharedPreferences.token = token
    }

    override fun saveExpirationDateInShared(expirationDate: String) {
        sharedPreferences.initPreferencesUtil(context)
        sharedPreferences.expirationDate = expirationDate
    }

    override fun getTokenFromShared(): String? {
        sharedPreferences.initPreferencesUtil(context)
        return sharedPreferences.token
    }

    override fun getExpirationDateFromShared(): String? {
        sharedPreferences.initPreferencesUtil(context)
        return sharedPreferences.expirationDate
    }

}