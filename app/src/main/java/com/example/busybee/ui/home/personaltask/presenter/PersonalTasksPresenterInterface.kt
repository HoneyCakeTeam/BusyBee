package com.example.busybee.ui.home.personaltask.presenter

interface PersonalTasksPresenterInterface {
    fun <T> getPersonalTasks(
        onSuccessCallback: (response: T) -> Unit,
        onFailureCallback: (error: Throwable) -> Unit
    )
}