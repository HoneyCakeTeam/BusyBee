package com.example.busybee.data.source

import com.example.busybee.data.models.*

interface RemoteDataSourceInterface {
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

}