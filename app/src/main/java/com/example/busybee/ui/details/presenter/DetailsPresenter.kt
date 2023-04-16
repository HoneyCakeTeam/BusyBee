package com.example.busybee.ui.details.presenter

import com.example.busybee.data.RepositoryInterface
import com.example.busybee.data.models.BaseResponse
import com.example.busybee.ui.details.view.DetailsViewInterface


class DetailsPresenter(
    private val repository: RepositoryInterface,
    private val detailsViewInterface: DetailsViewInterface
) {

    fun updateLocalTasksPersonalStatus(idTask: String, status: Int) {
        repository.updatePersonalTaskStatus(idTask, status)
    }

    fun updateLocalTasksTeamStatus(idTask: String, status: Int) {
        repository.updateTeamTaskStatus(idTask, status)
    }

    fun updateTasksPersonalStatus(idTask: String, status: Int) {
        repository.updateTasksPersonalStatus(
            idTask,
            status,
            ::onUpdatePersonalStatusSuccess,
            ::onUpdatePersonalStatusFailed
        )
    }

    private fun onUpdatePersonalStatusSuccess(response: BaseResponse<String>) {
        detailsViewInterface.onUpdatePersonalStatusSuccess(response)
    }

    private fun onUpdatePersonalStatusFailed(error: Throwable) {
        detailsViewInterface.onUpdatePersonalStatusFailed(error)
    }

    fun updateTasksTeamStatus(idTask: String, status: Int) {
        repository.updateTasksTeamStatus(
            idTask,
            status,
            ::onUpdateTeamStatusSuccess,
            ::onUpdateTeamStatusFailed
        )
    }

    private fun onUpdateTeamStatusSuccess(response: BaseResponse<String>) {
        detailsViewInterface.onUpdateTeamStatusSuccess(response)
    }

    private fun onUpdateTeamStatusFailed(error: Throwable) {
        detailsViewInterface.onUpdateTeamStatusFailed(error)
    }

}