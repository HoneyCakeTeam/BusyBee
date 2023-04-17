package com.example.busybee.ui.home.teamtask.done.presenter

import com.example.busybee.data.Repository
import com.example.busybee.ui.home.teamtask.done.view.TeamDoneViewInterface

class TeamDonePresenter(
    private val repository: Repository,
    private val teamDoneViewInterface: TeamDoneViewInterface
) {
    fun getLocalTeamDones() {
        teamDoneViewInterface.getLocalTeamDones(
            repository.getTeamTasks().filter { it.status == 2 })
    }
}