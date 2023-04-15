package com.example.busybee.ui.home

import com.example.busybee.data.RepositoryInterface
import com.example.busybee.data.models.BaseResponse
import com.example.busybee.data.models.PersonalToDo
import com.example.busybee.data.models.TeamToDo

class HomePresenter(
    private val repository: RepositoryInterface,
    private val homeViewInterface: HomeViewInterface
) {

    fun getAllTeamTasks() {
        repository.getAllTeamTasks(::onGetTeamTasksSuccess, ::onGetTeamTasksFailure)
    }

    fun setLocalTeamTasks(teamTasks: List<TeamToDo>) {
        return repository.setTeamTasks(teamTasks)
    }

    fun setLocalPersonalTasks(personalTasks: List<PersonalToDo>) {
        return repository.setPersonalTasks(personalTasks)
    }

    private fun onGetTeamTasksSuccess(response: BaseResponse<List<TeamToDo>>) {
        homeViewInterface.onTeamSuccessResponse(response)
    }

    private fun onGetTeamTasksFailure(error: Throwable) {
        homeViewInterface.onTeamFailureResponse(error)
    }

    fun getPersonalTasks() {
        repository.getPersonalTasks(::onGetPersonalTasksSuccess, ::onGetPersonalTasksFailure)
    }

    private fun onGetPersonalTasksSuccess(response: BaseResponse<List<PersonalToDo>>) {
        homeViewInterface.onPersonalSuccessResponse(response)
    }

    private fun onGetPersonalTasksFailure(error: Throwable) {
        homeViewInterface.onPersonalFailureResponse(error)
    }

}
