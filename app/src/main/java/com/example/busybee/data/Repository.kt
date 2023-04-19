package com.example.busybee.data

import com.example.busybee.data.models.*

interface Repository {

    fun saveToken(token: String?)
    fun getToken(): String?

    fun logIn(
        userName: String,
        password: String,
        onSuccessCallback: (response: BaseResponse<LoginResponseValue>) -> Unit,
        onFailureCallback: (error: Throwable) -> Unit
    )

    fun signUp(
        userName: String,
        password: String,
        onSuccessCallback: (response: BaseResponse<SignUpResponseValue>) -> Unit,
        onFailureCallback: (error: Throwable) -> Unit
    )

    fun getPersonalTasks(
        onSuccessCallback: (response: BaseResponse<List<PersonalToDo>>) -> Unit,
        onFailureCallback: (error: Throwable) -> Unit
    )

    fun getAllTeamTasks(
        onSuccessCallback: (response: BaseResponse<List<TeamToDo>>) -> Unit,
        onFailureCallback: (error: Throwable) -> Unit
    )

    fun createTeamToDo(
        title: String,
        description: String,
        assignee: String,
        onSuccessCallback: (response: BaseResponse<TeamToDo>) -> Unit,
        onFailureCallback: (error: Throwable) -> Unit
    )

    fun updateTasksTeamStatus(
        idTask: String,
        status: Int,
        onSuccessCallback: (response: BaseResponse<String>) -> Unit,
        onFailureCallback: (error: Throwable) -> Unit
    )

    fun updateTasksPersonalStatus(
        idTask: String, status: Int,
        onSuccessCallback: (response: BaseResponse<String>) -> Unit,
        onFailureCallback: (error: Throwable) -> Unit,
    )

    fun createPersonalToDo(
        title: String,
        description: String,
        onSuccessCallback: (response: BaseResponse<PersonalToDo>) -> Unit,
        onFailureCallback: (error: Throwable) -> Unit
    )

    fun getPersonalTasks(): List<PersonalToDo>

    fun setPersonalTasks(list: List<PersonalToDo>)

    fun updatePersonalTaskStatus(id: String, newStatus: Int)

    fun getTeamTasks(): List<TeamToDo>

    fun setTeamTasks(list: List<TeamToDo>)

    fun updateTeamTaskStatus(id: String, newStatus: Int)

    fun addPersonalToDo(todo: PersonalToDo)

    fun addTeamToDo(todo: TeamToDo)

    fun saveTheme(theme:Int)

    fun getTheme():Int
}