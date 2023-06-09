package com.example.busybee.ui.setting

interface SettingsView {

    fun getLocalPersonalDones(dones: Int)

    fun getLocalPersonalInProgress(inProgress: Int)

    fun getLocalPersonalTodos(todos: Int)

    fun getLocalTeamDones(dones: Int)

    fun getLocalTeamInProgress(inProgress: Int)

    fun getLocalTeamTodos(todos: Int)

}