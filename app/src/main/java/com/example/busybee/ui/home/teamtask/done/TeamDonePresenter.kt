package com.example.busybee.ui.home.teamtask.done

import com.example.busybee.data.Repository

class TeamDonePresenter(
    private val repository: Repository,
    private val view: TeamDoneView
) {
    fun getLocalTeamDones() {
        view.getLocalTeamDones(
            repository.getTeamTasks().filter { it.status == 2 })
    }
}