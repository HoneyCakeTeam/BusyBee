package com.example.busybee.ui.details

interface DetailsView {
    fun goToPersonalToDo()

    fun showPersonalErrorMsg(error: Throwable)

    fun goToTeamToDo()

    fun showTeamErrorMsg(error: Throwable)
}