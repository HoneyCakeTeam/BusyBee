package com.example.busybee.ui.home.teamtask.todo.presenter

import com.example.busybee.data.RepositoryInterface
import com.example.busybee.data.models.BaseResponse
import com.example.busybee.data.models.TeamToDo
import com.example.busybee.ui.home.teamtask.todo.view.TeamToDoViewInterface

class TeamToDoPresenter(
    private val repository: RepositoryInterface,
    private val teamToDoViewInterface: TeamToDoViewInterface
) {
    fun addTeamToDo(todo: TeamToDo) {
        repository.addTeamToDo(todo)
    }

    fun getLocalTeamTodos() {
        teamToDoViewInterface.getLocalTeamTodos(repository.getTeamTasks().filter { it.status == 0 })
    }

    fun teamCreateToDo(
        title: String,
        description: String,
        assignee: String,
    ) {
        repository.createTeamToDo(
            title,
            description,
            assignee,
            ::onCreateTeamTodoSuccess,
            ::onCreateTeamTodoFailure
        )
    }

    private fun onCreateTeamTodoSuccess(response: BaseResponse<TeamToDo>) {
        teamToDoViewInterface.onSuccessResponse(response)
    }

    private fun onCreateTeamTodoFailure(error: Throwable) {
        teamToDoViewInterface.onFailureResponse(error)
    }
}