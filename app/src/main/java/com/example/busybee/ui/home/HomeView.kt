package com.example.busybee.ui.home

import com.example.busybee.data.models.PersonalToDo
import com.example.busybee.data.models.TeamToDo

interface HomeView {
    fun goToTeamScreen(response: List<TeamToDo>)
    fun showErrorMsgOnTeamScreen(error: Throwable)
    fun goToPersonalScreen(response: List<PersonalToDo>)
    fun showErrorMsgOnPersonalScreen(error: Throwable)
}