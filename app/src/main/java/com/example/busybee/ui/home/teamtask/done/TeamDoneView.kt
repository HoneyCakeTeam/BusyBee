package com.example.busybee.ui.home.teamtask.done

import com.example.busybee.data.models.TeamToDo

interface TeamDoneView {
    fun getLocalTeamDones(dones: List<TeamToDo>)
}