package com.example.busybee.ui.home.teamtask.todo

import com.example.busybee.data.models.TeamToDo

interface TeamToDoView {
    fun createTeamToDo(title: String, description: String, assignee: String)
    fun addNewToDo(response: TeamToDo)
    fun showErrorMsg(error: Throwable)
    fun getLocalTeamTodos(todos: List<TeamToDo>)
}