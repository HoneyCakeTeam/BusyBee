package com.example.busybee.ui.home.teamtask.todo

import com.example.busybee.data.Repository
import com.example.busybee.data.models.BaseResponse
import com.example.busybee.data.models.TeamToDo
import com.example.busybee.utils.validator.Validator

class TeamToDoPresenter(
    private val repository: Repository,
    private val view: TeamToDoView,
    private val validator: Validator
) {
    fun addTeamToDo(todo: TeamToDo) {
        repository.addTeamToDo(todo)
    }

    fun getLocalTeamTodos() {
        view.getLocalTeamTodos(repository.getTeamTasks().filter { it.status == 0 })
    }

    fun teamCreateToDo(
        title: String,
        description: String,
        assignee: String,
    ) {
        val (isValid, errorMessage) = validator.validateTeamTodo(title, description, assignee)
        if (isValid) {
            view.hideValidationError()
            repository.createTeamToDo(
                title, description, assignee, ::onCreateTeamTodoSuccess, ::onCreateTeamTodoFailure
            )
        } else {
            view.showValidationError(
                errorMessage.first, errorMessage.second, errorMessage.third
            )
        }
    }

    private fun onCreateTeamTodoSuccess(response: BaseResponse<TeamToDo>) {
        view.addNewToDo(response.value)
    }

    private fun onCreateTeamTodoFailure(error: Throwable) {
        view.showErrorMsg(error)
    }
}