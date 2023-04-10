package com.example.busybee.ui.home.view.personalTask.view.presenter

import com.example.busybee.data.RepositoryInterface

class PersonalTasksPresenter(private val repository: RepositoryInterface) :
    PersonalTasksPresenterInterface {
    override fun <T> getPersonalTasks(
        onSuccessCallback: (response: T) -> Unit,
        onFailureCallback: (error: Throwable) -> Unit
    ) {
        repository.getPersonalTasks(onSuccessCallback, onFailureCallback)
    }

}