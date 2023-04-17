package com.example.busybee.ui.home.teamtask.done

import com.example.busybee.data.Repository

class TeamDonePresenter(
    private val repository: Repository,
    private val teamDoneViewInterface: TeamDoneView
) {
    fun getLocalTeamDones() {
        teamDoneViewInterface.getLocalTeamDones(
            repository.getTeamTasks().filter { it.status == 2 })
    }
}