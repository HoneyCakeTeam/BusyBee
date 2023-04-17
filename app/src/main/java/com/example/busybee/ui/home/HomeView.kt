package com.example.busybee.ui.home

import com.example.busybee.data.models.BaseResponse
import com.example.busybee.data.models.PersonalToDo
import com.example.busybee.data.models.TeamToDo

interface HomeView {
    fun onTeamSuccessResponse(response: BaseResponse<List<TeamToDo>>)
    fun onTeamFailureResponse(error: Throwable)
    fun onPersonalSuccessResponse(response: BaseResponse<List<PersonalToDo>>)
    fun onPersonalFailureResponse(error: Throwable)
}