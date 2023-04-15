package com.example.busybee.ui.home.teamtask.done.presenter

import com.example.busybee.data.RepositoryInterface
import com.example.busybee.ui.home.teamtask.done.view.TeamDoneViewInterface

class TeamDonePresenter(
    private val repository: RepositoryInterface,
    private val teamDoneViewInterface: TeamDoneViewInterface
) {
    fun getLocalTeamDones() {
        teamDoneViewInterface.getLocalTeamDones(
            repository.getTeamTasks().filter { it.status == 2 })
    }
}