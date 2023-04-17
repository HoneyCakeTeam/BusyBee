package com.example.busybee.ui.home.personaltask.inprogress.presenter

import com.example.busybee.data.Repository
import com.example.busybee.ui.home.personaltask.inprogress.view.PersonalInProgressViewInterface

class PersonalInProgressPresenter(
    private val repository: Repository,
    private val personalInProgressViewInterface: PersonalInProgressViewInterface
) {
    fun getLocalPersonalInProgress() {
        personalInProgressViewInterface.getLocalPersonalInProgress(
            repository.getPersonalTasks().filter { it.status == 1 })
    }
}