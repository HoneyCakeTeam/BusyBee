package com.example.busybee.ui.home.teamtask.inprogress

import com.example.busybee.data.models.TeamToDo

interface TeamInProgressView {
    fun getLocalTeamInProgress(inProgress: List<TeamToDo>)
}