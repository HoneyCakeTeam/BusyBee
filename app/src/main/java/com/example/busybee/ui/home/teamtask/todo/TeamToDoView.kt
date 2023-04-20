package com.example.busybee.ui.home.teamtask.todo

import com.example.busybee.data.models.TeamToDo

interface TeamToDoView {
    fun createTeamToDo(title: String, description: String, assignee: String)
    fun addNewToDo(response: TeamToDo)
    fun showErrorMsg(error: Throwable)
    fun getLocalTeamTodos(todos: List<TeamToDo>)
    fun showValidationError(
        titleErrorMessage: String?,
        descriptionErrorMessage: String?,
        assigneeErrorMessage: String?
    )

    fun hideValidationErrorThenCreateTeamTodo(
        title: String,
        description: String,
        assignee: String
    )
}