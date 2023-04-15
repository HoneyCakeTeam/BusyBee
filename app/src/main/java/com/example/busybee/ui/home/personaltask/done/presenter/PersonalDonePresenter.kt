package com.example.busybee.ui.home.personaltask.done.presenter

import com.example.busybee.data.RepositoryInterface
import com.example.busybee.ui.home.personaltask.done.view.PersonalDoneViewInterface

class PersonalDonePresenter(
    private val repository: RepositoryInterface,
    private val personalDoneViewInterface: PersonalDoneViewInterface
) {
    fun getLocalPersonalDones() {
        personalDoneViewInterface.getLocalPersonalDones(
            repository.getPersonalTasks().filter { it.status == 2 })
    }
}