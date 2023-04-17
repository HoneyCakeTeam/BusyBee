package com.example.busybee.ui.home.personaltask.inprogress

import com.example.busybee.data.Repository

class PersonalInProgressPresenter(
    private val repository: Repository,
    private val personalInProgressViewInterface: PersonalInProgressView
) {
    fun getLocalPersonalInProgress() {
        personalInProgressViewInterface.getLocalPersonalInProgress(
            repository.getPersonalTasks().filter { it.status == 1 })
    }
}