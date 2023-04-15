package com.example.busybee.ui.home.teamtask.inprogress.presenter

import com.example.busybee.data.RepositoryInterface
import com.example.busybee.ui.home.teamtask.inprogress.view.TeamInprogressViewInterface

class TeamInProgressPresenter(
    private val repository: RepositoryInterface,
    private val teamInProgressInterface: TeamInprogressViewInterface
) {

    fun getLocalTeamInProgress() {
        teamInProgressInterface.getLocalTeamInProgress(
            repository.getTeamTasks().filter { it.status == 1 })
    }
}