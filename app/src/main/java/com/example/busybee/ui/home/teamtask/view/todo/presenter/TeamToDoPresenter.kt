package com.example.busybee.ui.home.teamtask.view.todo.presenter

import com.example.busybee.data.RepositoryInterface

class TeamToDoPresenter(
    private val repository: RepositoryInterface
) : TeamToDoPresenterInterface {
    override fun <T> teamCreateToDo(
        title: String,
        description: String,
        assignee: String,
        onSuccessCallback: (response: T) -> Unit,
        onFailureCallback: (error: Throwable) -> Unit
    ) {
        repository.createTeamToDo(title,description,assignee,onSuccessCallback,onFailureCallback)
    }
}