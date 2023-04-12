package com.example.busybee.ui.home.teamtask.view.todo.view

import com.example.busybee.data.models.TeamCreateToDoResponse
import com.example.busybee.data.models.TeamToDoListResponse

interface TeamToDoViewInterface {
    fun teamCreateToDo(title: String ,description: String, assignee: String)
    fun onSuccessResponse(response: TeamCreateToDoResponse)
    fun onFailureResponse(error: Throwable)
}