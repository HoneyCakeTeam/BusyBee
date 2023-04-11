package com.example.busybee.ui.details.presenter


interface DetailsPresenterInterface {
    fun <T> updateTasksPersonalStatus(
        idTask: String, status: Int,
        onSuccessCallback: (response: T) -> Unit,
        onFailureCallback: (error: Throwable) -> Unit,
    )
}