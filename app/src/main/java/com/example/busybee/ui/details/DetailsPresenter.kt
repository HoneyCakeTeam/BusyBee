package com.example.busybee.ui.details

import com.example.busybee.data.Repository
import com.example.busybee.data.models.BaseResponse


class DetailsPresenter(
    private val repository: Repository,
    private val view: DetailsView
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
        view.goToPersonalToDo()
    }

    private fun onUpdatePersonalStatusFailed(error: Throwable) {
        view.showPersonalErrorMsg(error)
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
        view.goToTeamToDo()
    }

    private fun onUpdateTeamStatusFailed(error: Throwable) {
        view.showTeamErrorMsg(error)
    }

}