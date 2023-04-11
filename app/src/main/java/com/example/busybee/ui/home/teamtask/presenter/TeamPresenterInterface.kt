package com.example.busybee.ui.home.teamtask.presenter

interface TeamPresenterInterface {
    fun <T> getAllTeamTasks(
        onSuccessCallback: (response: T) -> Unit,
        onFailureCallback: (error: Throwable) -> Unit
    )
}