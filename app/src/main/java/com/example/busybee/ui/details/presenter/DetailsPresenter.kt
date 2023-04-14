package com.example.busybee.ui.details.presenter

import com.example.busybee.data.RepositoryInterface
import com.example.busybee.data.models.BaseResponse
import com.example.busybee.data.models.LoginResponseValue
import com.example.busybee.data.source.RemoteDataSourceInterface
import com.example.busybee.ui.details.view.DetailsViewInterface


class DetailsPresenter(
    private val repository: RepositoryInterface,
    private val detailsViewInterface: DetailsViewInterface
) {
    fun <T> updateTasksPersonalStatus(idTask: String, status: Int) {
        repository.updateTasksPersonalStatus(idTask, status, ::onSuccess, ::onFailure)
    }

    fun <T> updateTasksTeamStatus(idTask: String, status: Int) {
        repository.updateTasksTeamStatus(idTask, status, ::onSuccess, ::onFailure)
    }

    private fun onSuccess(response: BaseResponse<String>) {
        detailsViewInterface.onSuccessResponse(response)
    }

    private fun onFailure(error: Throwable) {
        detailsViewInterface.onFailureResponse(error)
    }
}