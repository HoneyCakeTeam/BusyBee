package com.example.busybee.ui.details.presenter

import com.example.busybee.data.RepositoryInterface
import com.example.busybee.data.source.RemoteDataSourceInterface


class DetailsPresenter(
    private val repository: RepositoryInterface,
) : DetailsPresenterInterface {
    override fun <T> updateTasksPersonalStatus(
        idTask: String,
        status: Int,
        onSuccessCallback: (response: T) -> Unit,
        onFailureCallback: (error: Throwable) -> Unit,
    ) {
        repository.updateTasksPersonalStatus(idTask, status, onSuccessCallback, onFailureCallback)

    }

    override fun <T> updateTasksTeamStatus(
        idTask: String,
        status: Int,
        onSuccessCallback: (response: T) -> Unit,
        onFailureCallback: (error: Throwable) -> Unit
    ) {
        repository.updateTasksTeamStatus(idTask, status, onSuccessCallback, onFailureCallback)
    }

}