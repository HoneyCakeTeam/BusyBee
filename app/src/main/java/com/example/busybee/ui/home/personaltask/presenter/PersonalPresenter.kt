package com.example.busybee.ui.home.personaltask.presenter

import com.example.busybee.data.RepositoryInterface

class PersonalPresenter(private val repository: RepositoryInterface) : PersonalPresenterInterface {
    override fun <T> personalCreateToDo(
        title: String,
        description: String,
        onSuccessCallback: (response: T) -> Unit,
        onFailureCallback: (error: Throwable) -> Unit
    ) {
        repository.personalCreateToDo(title, description, onSuccessCallback, onFailureCallback)
    }
}