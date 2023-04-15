package com.example.busybee.ui.home.teamtask.inprogress.view

import com.example.busybee.data.models.TeamToDo

interface TeamInprogressViewInterface {
    fun getLocalTeamInProgress(inProgress: List<TeamToDo>)
}