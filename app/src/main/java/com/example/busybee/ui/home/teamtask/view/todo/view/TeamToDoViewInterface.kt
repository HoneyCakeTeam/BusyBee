package com.example.busybee.ui.home.teamtask.view.todo.view

import com.example.busybee.data.models.BaseTeamResponse
import com.example.busybee.data.models.TeamToDo

interface TeamToDoViewInterface {
    fun teamCreateToDo(title: String, description: String, assignee: String)
    fun onSuccessResponse(response: BaseTeamResponse<TeamToDo>)
    fun onFailureResponse(error: Throwable)
}