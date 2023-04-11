package com.example.busybee.ui.home

import com.example.busybee.data.models.PersonalGetToDoListResponse
import com.example.busybee.data.models.TeamToDoListResponse

interface HomeViewInterface {
    fun getAllTeamTasks()
    fun onTeamSuccessResponse(response: TeamToDoListResponse)
    fun onTeamFailureResponse(error: Throwable)

    fun getAllPersonalTasks()
    fun onPersonalSuccessResponse(response: PersonalGetToDoListResponse)
    fun onPersonalFailureResponse(error: Throwable)
}