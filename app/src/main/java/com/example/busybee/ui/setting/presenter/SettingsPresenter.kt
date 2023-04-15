package com.example.busybee.ui.setting.presenter

import com.example.busybee.data.RepositoryInterface
import com.example.busybee.ui.setting.view.SettingsViewInterface

class SettingsPresenter(
    private val repository: RepositoryInterface,
    private val settingsViewInterface: SettingsViewInterface
) {

    fun getLocalPersonalDones() {
        settingsViewInterface.getLocalPersonalDones(
            repository.getPersonalTasks().filter { it.status == 2 }.size
        )
    }

    fun getLocalPersonalInProgress() {
        settingsViewInterface.getLocalPersonalInProgress(
            repository.getPersonalTasks().filter { it.status == 1 }.size
        )
    }

    fun getLocalPersonalTodos() {
        settingsViewInterface.getLocalPersonalTodos(
            repository.getPersonalTasks().filter { it.status == 0 }.size
        )
    }

    fun getLocalTeamDones() {
        settingsViewInterface.getLocalTeamDones(
            repository.getTeamTasks().filter { it.status == 2 }.size
        )
    }

    fun getLocalTeamInProgress() {
        settingsViewInterface.getLocalTeamInProgress(
            repository.getTeamTasks().filter { it.status == 1 }.size
        )
    }

    fun getLocalTeamTodos() {
        settingsViewInterface.getLocalTeamTodos(
            repository.getTeamTasks().filter { it.status == 0 }.size
        )
    }

}