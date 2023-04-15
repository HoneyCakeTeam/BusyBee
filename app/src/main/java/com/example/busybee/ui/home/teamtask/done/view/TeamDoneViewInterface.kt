package com.example.busybee.ui.home.teamtask.done.view

import com.example.busybee.data.models.TeamToDo

interface TeamDoneViewInterface {
    fun getLocalTeamDones(dones: List<TeamToDo>)
}