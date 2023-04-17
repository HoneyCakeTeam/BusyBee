package com.example.busybee.ui.home.teamtask.inprogress

import com.example.busybee.data.Repository

class TeamInProgressPresenter(
    private val repository: Repository,
    private val teamInProgressInterface: TeamInprogressView
) {

    fun getLocalTeamInProgress() {
        teamInProgressInterface.getLocalTeamInProgress(
            repository.getTeamTasks().filter { it.status == 1 })
    }
}