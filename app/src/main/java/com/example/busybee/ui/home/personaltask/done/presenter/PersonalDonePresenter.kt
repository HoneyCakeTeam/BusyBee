package com.example.busybee.ui.home.personaltask.done.presenter

import com.example.busybee.data.Repository
import com.example.busybee.ui.home.personaltask.done.view.PersonalDoneViewInterface

class PersonalDonePresenter(
    private val repository: Repository,
    private val personalDoneViewInterface: PersonalDoneViewInterface
) {
    fun getLocalPersonalDones() {
        personalDoneViewInterface.getLocalPersonalDones(
            repository.getPersonalTasks().filter { it.status == 2 })
    }
}