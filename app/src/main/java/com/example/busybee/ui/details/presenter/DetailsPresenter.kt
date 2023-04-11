package com.example.busybee.ui.details.presenter

import com.example.busybee.data.RepositoryInterface
import com.example.busybee.ui.details.view.DetailsViewInterface


class DetailsPresenter(
    val detailsViewInterface: DetailsViewInterface,
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

}