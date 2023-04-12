package com.example.busybee.data

interface RepositoryInterface {
    fun <T> logIn(
        userName: String,
        password: String,
        onSuccessCallback: (response: T) -> Unit,
        onFailureCallback: (error: Throwable) -> Unit
    )

    fun saveTokenInShared(token: String)
    fun saveExpirationDateInShared(expirationDate: String)

    fun <T> getPersonalTasks(
        onSuccessCallback: (response: T) -> Unit,
        onFailureCallback: (error: Throwable) -> Unit
    )

    fun <T> getAllTeamTasks(
        onSuccessCallback: (response: T) -> Unit,
        onFailureCallback: (error: Throwable) -> Unit
    )

    fun <T> createTeamToDo(
        title: String,
        description: String,
        assignee: String,
        onSuccessCallback: (response: T) -> Unit,
        onFailureCallback: (error: Throwable) -> Unit
    )

    fun <T> signUp(
        userName: String,
        password: String,
        onSuccessCallback: (response: T) -> Unit,
        onFailureCallback: (error: Throwable) -> Unit
    )


    fun <T> updateTasksTeamStatus(
        idTask: String,
        status: Int,
        onSuccessCallback: (response: T) -> Unit,
        onFailureCallback: (error: Throwable) -> Unit
    )

    fun <T> updateTasksPersonalStatus(
        idTask: String, status: Int,
        onSuccessCallback: (response: T) -> Unit,
        onFailureCallback: (error: Throwable) -> Unit,
    )

    fun <T> createPersonalToDo(
        title: String,
        description: String,
        onSuccessCallback: (response: T) -> Unit,
        onFailureCallback: (error: Throwable) -> Unit
    )

    fun getTokenFromShared(): String?
    fun getExpirationDateFromShared():String?
}