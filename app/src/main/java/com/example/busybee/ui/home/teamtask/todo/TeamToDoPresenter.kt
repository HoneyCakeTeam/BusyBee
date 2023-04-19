package com.example.busybee.ui.home.teamtask.todo

import com.example.busybee.data.Repository
import com.example.busybee.data.models.BaseResponse
import com.example.busybee.data.models.TeamToDo

class TeamToDoPresenter(
    private val repository: Repository,
    private val teamToDoViewInterface: TeamToDoView
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