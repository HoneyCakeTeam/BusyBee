package com.example.busybee.ui.home.personaltask.presenter

import com.example.busybee.data.source.RemoteDataSourceInterface

class PersonalPresenter(private val repository: RemoteDataSourceInterface) :
    PersonalPresenterInterface {
    override fun <T> getPersonalTasks(
        onSuccessCallback: (response: T) -> Unit,
        onFailureCallback: (error: Throwable) -> Unit
    ) {
        repository.getPersonalTasks(onSuccessCallback, onFailureCallback)
    }

    override fun <T> personalCreateToDo(
        title: String,
        description: String,
        onSuccessCallback: (response: T) -> Unit,
        onFailureCallback: (error: Throwable) -> Unit
    ) {
        repository.createPersonalToDo(title, description, onSuccessCallback, onFailureCallback)
    }
}