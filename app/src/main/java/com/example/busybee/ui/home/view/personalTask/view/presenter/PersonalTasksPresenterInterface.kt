package com.example.busybee.ui.home.view.personalTask.view.presenter

interface PersonalTasksPresenterInterface {

    fun <T> getPersonalTasks(
        onSuccessCallback: (response: T) -> Unit,
        onFailureCallback: (error: Throwable) -> Unit
    )
}