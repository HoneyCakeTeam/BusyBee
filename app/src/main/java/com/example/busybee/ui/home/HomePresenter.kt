package com.example.busybee.ui.home

import com.example.busybee.data.Repository
import com.example.busybee.data.models.BaseResponse
import com.example.busybee.data.models.PersonalToDo
import com.example.busybee.data.models.TeamToDo

class HomePresenter(
    private val repository: Repository,
    private val view: HomeView
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
        view.showDataOnTeamScreen(response.value)
    }

    private fun onGetTeamTasksFailure(error: Throwable) {
        view.showErrorMsgOnTeamScreen(error)
    }

    fun getPersonalTasks() {
        repository.getPersonalTasks(::onGetPersonalTasksSuccess, ::onGetPersonalTasksFailure)
    }

    private fun onGetPersonalTasksSuccess(response: BaseResponse<List<PersonalToDo>>) {
        view.showDataOnPersonalScreen(response.value)
    }

    private fun onGetPersonalTasksFailure(error: Throwable) {
        view.showErrorMsgOnPersonalScreen(error)
    }

}
