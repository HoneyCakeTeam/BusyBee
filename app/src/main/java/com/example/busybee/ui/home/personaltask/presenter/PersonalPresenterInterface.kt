package com.example.busybee.ui.home.personaltask.presenter

interface PersonalPresenterInterface {

    fun <T> personalCreateToDo(title: String,
                               description: String,
                               onSuccessCallback: (response: T) -> Unit,
                               onFailureCallback: (error: Throwable) -> Unit )
}