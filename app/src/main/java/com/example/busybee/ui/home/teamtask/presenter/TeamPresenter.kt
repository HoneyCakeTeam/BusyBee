package com.example.busybee.ui.home.teamtask.presenter

import com.example.busybee.data.source.RemoteDataSourceInterface

class TeamPresenter(private val repository: RemoteDataSourceInterface)
    : TeamPresenterInterface {
    override fun <T> getAllTeamTasks(
        onSuccessCallback: (response: T) -> Unit,
        onFailureCallback: (error: Throwable) -> Unit
    ) {
        repository.getAllTeamTasks(onSuccessCallback, onFailureCallback)
    }
}