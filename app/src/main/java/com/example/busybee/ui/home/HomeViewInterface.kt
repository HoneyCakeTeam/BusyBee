package com.example.busybee.ui.home

import com.example.busybee.data.models.BaseTeamResponse
import com.example.busybee.data.models.PersonalToDoListResponse
import com.example.busybee.data.models.TeamToDo

interface HomeViewInterface {
    fun getAllTeamTasks()
    fun onTeamSuccessResponse(response: BaseTeamResponse<List<TeamToDo>>)
    fun onTeamFailureResponse(error: Throwable)

    fun getAllPersonalTasks()
    fun onPersonalSuccessResponse(response: PersonalToDoListResponse)
    fun onPersonalFailureResponse(error: Throwable)
}