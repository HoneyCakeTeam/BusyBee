package com.example.busybee.ui.home.view.teamTask.view.toDo.view

import com.example.busybee.data.models.TeamToDoListResponse

interface TeamToDoViewInterface {
    fun teamCreateToDo(title: String ,description: String, assignee: String)
    fun onSuccessResponse(response: TeamToDoListResponse)
    fun onFailureResponse(error: Throwable)
}