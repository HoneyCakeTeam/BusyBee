package com.example.busybee.ui.home.personaltask.inprogress.view

import com.example.busybee.data.models.PersonalToDo

interface PersonalInProgressViewInterface {
    fun getLocalPersonalInProgress(inProgress: List<PersonalToDo>)
}