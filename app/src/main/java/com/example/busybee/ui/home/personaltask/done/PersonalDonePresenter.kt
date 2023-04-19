package com.example.busybee.ui.home.personaltask.done

import com.example.busybee.data.Repository

class PersonalDonePresenter(
    private val repository: Repository,
    private val view: PersonalDoneView
) {
    fun getLocalPersonalDones() {
        view.getLocalPersonalDones(
            repository.getPersonalTasks().filter { it.status == 2 })
    }
}