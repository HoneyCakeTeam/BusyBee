package com.example.busybee.ui.home.personaltask.presenter

import com.example.busybee.data.RepositoryInterface

class PersonalPresenter(private val repository: RepositoryInterface) :
    PersonalPresenterInterface {
    override fun <T> getPersonalTasks(
        onSuccessCallback: (response: T) -> Unit,
        onFailureCallback: (error: Throwable) -> Unit
    ) {
        repository.getPersonalTasks(onSuccessCallback, onFailureCallback)
    }

}