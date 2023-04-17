package com.example.busybee.ui.home.teamtask.todo

import com.example.busybee.data.models.BaseResponse
import com.example.busybee.data.models.TeamToDo

interface TeamToDoView {
    fun teamCreateToDo(title: String, description: String, assignee: String)
    fun onSuccessResponse(response: BaseResponse<TeamToDo>)
    fun onFailureResponse(error: Throwable)
    fun getLocalTeamTodos(todos: List<TeamToDo>)
}