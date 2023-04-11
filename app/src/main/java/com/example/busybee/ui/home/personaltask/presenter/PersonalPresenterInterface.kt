package com.example.busybee.ui.home.personaltask.presenter

interface PersonalPresenterInterface {
    fun <T> getPersonalTasks(
        onSuccessCallback: (response: T) -> Unit,
        onFailureCallback: (error: Throwable) -> Unit
    )
}