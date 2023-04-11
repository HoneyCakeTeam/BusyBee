package com.example.busybee.ui.home.view.teamTask.view.toDo.presenter

interface TeamToDoPresenterInterface {

    fun <T> teamCreateToDo(title: String,
                           description: String,
                           assignee: String ,
                           onSuccessCallback: (response: T) -> Unit,
                           onFailureCallback: (error: Throwable) -> Unit )
}