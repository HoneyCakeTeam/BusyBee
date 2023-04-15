package com.example.busybee.data

import android.content.Context
import com.example.busybee.data.models.PersonalToDo
import com.example.busybee.data.models.TeamToDo
import com.example.busybee.data.source.RemoteDataSourceInterface
import com.example.busybee.utils.SharedPreferencesUtils

class Repository(
    private val remoteDataSource: RemoteDataSourceInterface,
    private val sharedPreferences: SharedPreferencesUtils,
    private val context: Context
) : RepositoryInterface {


    override fun <T> logIn(
        userName: String, password: String, onSuccessCallback: (response: T) -> Unit,
        onFailureCallback: (error: Throwable) -> Unit
    ) {
        remoteDataSource.logIn<T>(userName, password, onSuccessCallback, onFailureCallback)

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
        remoteDataSource.createTeamToDo<T>(
            title,
            description,
            assignee,
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
        remoteDataSource.createPersonalToDo<T>(
            title,
            description,
            onSuccessCallback,
            onFailureCallback
        )
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
        remoteDataSource.updateTasksPersonalStatus<T>(
            idTask, status,
            onSuccessCallback, onFailureCallback
        )
    }


    override fun <T> updateTasksTeamStatus(
        idTask: String,
        status: Int,
        onSuccessCallback: (response: T) -> Unit,
        onFailureCallback: (error: Throwable) -> Unit
    ) {

        remoteDataSource.updateTasksTeamStatus<T>(
            idTask,
            status,
            onSuccessCallback,
            onFailureCallback
        )
    }

    override fun saveToken(token: String?) {
        sharedPreferences.initPreferencesUtil(context)
        sharedPreferences.token = token
    }

    override fun saveExpirationDate(expirationDate: String) {
        sharedPreferences.initPreferencesUtil(context)
        sharedPreferences.expirationDate = expirationDate
    }

    override fun getToken(): String? {
        sharedPreferences.initPreferencesUtil(context)
        return sharedPreferences.token
    }

    override fun getExpirationDate(): String? {
        sharedPreferences.initPreferencesUtil(context)
        return sharedPreferences.expirationDate
    }

    fun getPersonalTasks(): List<PersonalToDo> {
        return LocalDataSource.personalTasks
    }

    fun setPersonalTasks(list: List<PersonalToDo>) {
        LocalDataSource.personalTasks = list as MutableList<PersonalToDo>
    }

    fun updatePersonalTaskStatus(id: String, newStatus: Int) {
        LocalDataSource.personalTasks.first { it.id == id }.status = newStatus
    }

    fun getTeamTasks(): List<TeamToDo> {
        return LocalDataSource.teamTasks
    }

    fun setTeamTasks(list: List<TeamToDo>) {
        LocalDataSource.teamTasks = list as MutableList<TeamToDo>
    }

    fun updateTeamTaskStatus(id: String, newStatus: Int) {
        LocalDataSource.teamTasks.first { it.id == id }.status = newStatus
    }
}

private object LocalDataSource {
    var personalTasks = mutableListOf<PersonalToDo>()
    var teamTasks = mutableListOf<TeamToDo>()
}