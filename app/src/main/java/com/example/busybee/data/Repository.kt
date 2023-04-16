package com.example.busybee.data

import android.util.Log
import com.example.busybee.data.models.*
import com.example.busybee.data.source.RemoteDataSourceInterface
import com.example.busybee.utils.SharedPreferencesUtils

class Repository(
    private val remoteDataSource: RemoteDataSourceInterface,
    private val sharedPreferences: SharedPreferencesUtils,
) : RepositoryInterface {

    override fun logIn(
        userName: String,
        password: String,
        onSuccessCallback: (response: BaseResponse<LoginResponseValue>) -> Unit,
        onFailureCallback: (error: Throwable) -> Unit
    ) {
        remoteDataSource.logIn(userName, password, onSuccessCallback, onFailureCallback)

    }

    override fun signUp(
        userName: String,
        password: String,
        onSuccessCallback: (response: BaseResponse<SignUpResponseValue>) -> Unit,
        onFailureCallback: (error: Throwable) -> Unit
    ) {
        remoteDataSource.signUp(userName, password, onSuccessCallback, onFailureCallback)
    }

    override fun getAllTeamTasks(
        onSuccessCallback: (response: BaseResponse<List<TeamToDo>>) -> Unit,
        onFailureCallback: (error: Throwable) -> Unit
    ) {
        remoteDataSource.getAllTeamTasks(onSuccessCallback, onFailureCallback)
    }

    override fun createTeamToDo(
        title: String,
        description: String,
        assignee: String,
        onSuccessCallback: (response: BaseResponse<TeamToDo>) -> Unit,
        onFailureCallback: (error: Throwable) -> Unit
    ) {
        remoteDataSource.createTeamToDo(
            title,
            description,
            assignee,
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
        remoteDataSource.createPersonalToDo(
            title,
            description,
            onSuccessCallback,
            onFailureCallback
        )
    }

    override fun getPersonalTasks(
        onSuccessCallback: (response: BaseResponse<List<PersonalToDo>>) -> Unit,
        onFailureCallback: (error: Throwable) -> Unit
    ) {
        remoteDataSource.getPersonalTasks(onSuccessCallback, onFailureCallback)
    }

    override fun updateTasksPersonalStatus(
        idTask: String,
        status: Int,
        onSuccessCallback: (response: BaseResponse<String>) -> Unit,
        onFailureCallback: (error: Throwable) -> Unit,
    ) {
        remoteDataSource.updateTasksPersonalStatus(
            idTask,
            status,
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
        remoteDataSource.updateTasksTeamStatus(
            idTask,
            status,
            onSuccessCallback,
            onFailureCallback
        )
    }

    override fun saveToken(token: String?) {
        sharedPreferences.token = token
    }

    override fun getToken(): String? {
        return sharedPreferences.token
    }

    override fun getPersonalTasks(): List<PersonalToDo> {
        return LocalDataSource.personalTasks
    }

    override fun setPersonalTasks(list: List<PersonalToDo>) {
        LocalDataSource.personalTasks = list as MutableList<PersonalToDo>
    }

    override fun updatePersonalTaskStatus(id: String, newStatus: Int) {
        LocalDataSource.personalTasks.first { it.id == id }.status = newStatus
    }

    override fun getTeamTasks(): List<TeamToDo> {
        return LocalDataSource.teamTasks
    }

    override fun setTeamTasks(list: List<TeamToDo>) {
        LocalDataSource.teamTasks = list as MutableList<TeamToDo>
    }

    override fun updateTeamTaskStatus(id: String, newStatus: Int) {
        LocalDataSource.teamTasks.first { it.id == id }.status = newStatus
    }

    override fun addPersonalToDo(todo: PersonalToDo) {
        LocalDataSource.personalTasks.add(todo)
    }

    override fun addTeamToDo(todo: TeamToDo) {
        LocalDataSource.teamTasks.add(todo)
    }
}

private object LocalDataSource {
    var personalTasks = mutableListOf<PersonalToDo>()
    var teamTasks = mutableListOf<TeamToDo>()
}