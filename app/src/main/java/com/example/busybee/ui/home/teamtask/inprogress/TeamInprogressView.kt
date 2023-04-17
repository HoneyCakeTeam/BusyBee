package com.example.busybee.ui.home.teamtask.inprogress

import com.example.busybee.data.models.TeamToDo

interface TeamInprogressView {
    fun getLocalTeamInProgress(inProgress: List<TeamToDo>)
}